import React, {Component} from "react";
import {Alert, Button, Table} from 'reactstrap';
import axios from "axios";
import PriceChart from "./PriceChart";
import commaNumber from "comma-number";

export default class MarketRow extends Component {

    constructor(props) {
        super(props);
    }

    buyResource(event, resource) {
        axios.get("/api/market/buy?resource=" + resource.resourceName)
            .then(
                () => {
                    this.props.updateUser();
                    this.setState({
                        alertVisible: true,
                        alertColor: "success",
                        alertText: "Resource Purchased."
                    })
                },
                (error) => {
                    this.setState({
                        alertVisible: true,
                        alertColor: "danger",
                        alertText: error.response.data.message
                    })
                }
            )
    }

    sellResource(event, resource) {
        axios.get("/api/market/sell?resource=" + resource.resourceName)
            .then(
                () => {
                    this.props.updateUser();
                    this.setState({
                        alertVisible: true,
                        alertColor: "success",
                        alertText: "Resource Sold."
                    })
                },
                (error) => {
                    this.setState({
                        alertVisible: true,
                        alertColor: "danger",
                        alertText: error.response.data.message
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

        return (
            <div className={"pp-page"}>
                {coinsBar}
                <Table>
                    <thead className={"thead-dark"}>
                    {headers}
                    </thead>
                    <tbody className={"table-light"}>
                    {this.state.resources.map(this.renderResourceRow.bind(this))}
                    </tbody>
                </Table>
                <Alert className={"market-buy-alert"}
                       color={this.state.alertColor}
                       isOpen={this.state.alertVisible}
                       toggle={onAlertDismiss}>
                    {this.state.alertText}
                </Alert>
            </div>
        )
    }

    renderResourceRow(resource) {
        return (
            <tr key={"resource-row-" + resource.resourceName}>
                <th className={"resource-name-col"}>{resource.resourceName}</th>
                <td>
                    {commaNumber(resource.price) + " Coins"}
                </td>
                <td>
                    <PriceChart resource={resource}
                                height={100}/>
                </td>
                {this.renderUserColumns(resource, this.props.user)}
            </tr>
        )
    }

    renderUserColumns(resource, user) {
        if (user) {
            return (
                [
                    <td>{user.resources[resource.resourceName] || 0}</td>,
                    <td>
                        <Button color="primary"
                                onClick={event => this.buyResource(event, resource).bind(this)}>
                            Buy {resource.name}
                        </Button>
                    </td>,
                    <td>
                        <Button color="primary"
                                onClick={event => this.sellResource(event, resource).bind(this)}>
                            Sell {resource.name}
                        </Button>
                    </td>
                ]
            )
        } else {
            return [];
        }
    }
}