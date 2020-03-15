import React, {Component} from "react";
import {Button} from 'reactstrap';
import axios from "axios";
import PriceChart from "./PriceChart";
import commaNumber from "comma-number";

export default class MarketRow extends Component {

    buyResource(event, resource) {
        axios.get("/api/market/buy?resource=" + resource.resourceName)
            .then(
                () => {
                    this.props.updateUser();
                }
            )
    }

    sellResource(event, resource) {
        axios.get("/api/market/sell?resource=" + resource.resourceName)
            .then(
                () => {
                    this.props.updateUser();
                }
            )
    }

    render() {
        let resource = this.props.resource;

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
                                onClick={event => this.buyResource(event, resource).bind(this)}
                                disabled={this.disableBuyButton(user, resource)}>
                            Buy {resource.name}
                        </Button>
                    </td>,
                    <td>
                        <Button color="primary"
                                onClick={event => this.sellResource(event, resource).bind(this)}
                                disabled={this.disableSellButton(user, resource)}>
                            Sell {resource.name}
                        </Button>
                    </td>
                ]
            )
        } else {
            return [];
        }
    }

    disableSellButton(user, resource) {
        let userHasResource = user.resources[resource.resourceName] > 1;
        return !userHasResource;
    }

    disableBuyButton(user, resource) {
        let userHasEnoughCoins = user.coins > resource.price;
        return !userHasEnoughCoins;
    }
}
