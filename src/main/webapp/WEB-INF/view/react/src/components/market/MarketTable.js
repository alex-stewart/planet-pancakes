import React, {Component} from "react";
import {Alert, Table} from 'reactstrap';
import MarketRow from "./MarketRow";
import _ from "lodash";

const thinColStyle = {
    width: '150px'
};

export default class MarketTable extends Component {

    constructor(props) {
        super(props);
        this.state = {
            resources: [],
            alertText: null,
            alertVisible: false
        }
    }

    render() {
        const onAlertDismiss = () => this.setState({
            alertVisible: false
        });

        const loggedInColumns = [
            <tr key={"header-row"}>
                <th style={thinColStyle}>Resource</th>
                <th style={thinColStyle}>Price</th>
                <th>Price Trend</th>
                <th style={thinColStyle}>Quantity Owned</th>
                <th style={thinColStyle}>Buy</th>
                <th style={thinColStyle}>Sell</th>
            </tr>
        ];

        const defaultColumns = [
            <tr key={"header-row-default"}>
                <th style={thinColStyle}>Resource</th>
                <th style={thinColStyle}>Price</th>
                <th>Price Trend</th>
            </tr>
        ];

        let user = this.props.user;
        let headers = user ? loggedInColumns : defaultColumns;
        let resourceRows = this.renderResourceRows(this.props.resources, this.props.user, this.props.updateUser);

        return (
            <div>
                <div>RESOURCE TYPE</div>
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
            return <MarketRow key={'market-row-' + resource.resourceName}
                              resource={resource}
                              user={user}
                              updateUser={updateUserFunction}
                              addErrorAlert={addErrorAlert}/>
        });
    }
}
