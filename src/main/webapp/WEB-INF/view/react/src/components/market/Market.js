import React, {Component} from "react";
import axios from "axios";
import commaNumber from "comma-number";
import {Helmet} from "react-helmet";
import MarketTable from "./MarketTable";
import {Alert} from "reactstrap";

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

    setAlert(message) {
        this.setState({
            alertVisible: true,
            alertText: message
        })
    }

    render() {
        const onAlertDismiss = () => this.setState({
            alertVisible: false
        });

        let user = this.props.user;
        let coinsBar = user ? <div>Coins: {commaNumber(this.props.user.coins)}</div> : null;

        return (
            <div className={"pp-page"}>
                <Helmet>
                    <title>{"PP - Market"}</title>
                </Helmet>
                {coinsBar}
                <MarketTable user={user}
                             resources={this.state.resources}
                             updateUser={this.props.updateUser}
                             setAlert={this.setAlert}
                             resourceType={"Minerals & Metals"}/>
                <Alert className={"market-buy-alert"}
                       color={"danger"}
                       isOpen={this.state.alertVisible}
                       toggle={onAlertDismiss}>
                    {this.state.alertText}
                </Alert>
            </div>
        )
    }
}
