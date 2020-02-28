import React, {Component} from "react";
import {Alert, Table} from 'reactstrap';
import axios from "axios";
import commaNumber from "comma-number";
import MarketRow from "./MarketRow";
import _ from "lodash";
import {Helmet} from "react-helmet";

export default class Market extends Component {

    constructor(props) {
        super(props);
        this.state = {
            resources: [],
            alertText: null,
            alertVisible: false
        }
    }

    componentDidMount() {
        this.getResources();
    }

    getResources() {
        axios.get("/api/resources")
            .then(
                (result) => {
                    this.setState({
                        resources: result.data
                    })
                }
            )
    }

    render() {
        const onAlertDismiss = () => this.setState({
            alertVisible: false
        });

        const loggedInColumns = [
            <tr key={"header-row"}>
                <th>Resource</th>
                <th>Price</th>
                <th>Price Trend</th>
                <th>Quantity Owned</th>
                <th>Buy</th>
                <th>Sell</th>
            </tr>
        ];

        const defaultColumns = [
            <tr key={"header-row-default"}>
                <th>Resource</th>
                <th>Price</th>
                <th>Price Trend</th>
            </tr>
        ];

        let user = this.props.user;
        let coinsBar = user ? <div>Coins: {commaNumber(this.props.user.coins)}</div> : null;
        let headers = user ? loggedInColumns : defaultColumns;

        let resourceRows = this.renderResourceRows(this.state.resources, this.props.user, this.props.updateUser);

        return (
            <div className={"pp-page"}>
                <Helmet>
                    <title>{"PP - Market"}</title>
                </Helmet>
                {coinsBar}
                <Table>
                    <thead className={"thead-dark"}>
                    {headers}
                    </thead>
                    <tbody className={"table-light"}>
                    {resourceRows}
                    </tbody>
                </Table>
                <Alert className={"market-buy-alert"}
                       color={"danger"}
                       isOpen={this.state.alertVisible}
                       toggle={onAlertDismiss}>
                    {this.state.alertText}
                </Alert>
            </div>
        )
    }

    renderResourceRows(resources, user, updateUserFunction) {
        const addErrorAlert = (message) => this.setState({
            alertVisible: true,
            alertText: message
        });

        return _.map(resources, function (resource) {
            return <MarketRow resource={resource}
                              user={user}
                              updateUser={updateUserFunction}
                              addErrorAlert={addErrorAlert}/>
        });
    }
}