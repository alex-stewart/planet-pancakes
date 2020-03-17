import React, {Component} from "react";
import {Button} from 'reactstrap';
import axios from "axios";
import PriceChart from "./PriceChart";
import commaNumber from "comma-number";

const notAllowedCursor = {
    cursor: "not-allowed"
};

const defaultCursor = {
    cursor: "default"
};

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
                <th className={"capitalise-first-letter"}>{resource.resourceName}</th>
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
            let canSell = user.resources[resource.resourceName] > 0;
            let canBuy = user.coins >= resource.price;

            return (
                [
                    <td>{user.resources[resource.resourceName] || 0}</td>,
                    <td>
                        <Button color="primary"
                                style={canBuy ? defaultCursor : notAllowedCursor}
                                onClick={event => this.buyResource(event, resource).bind(this)}
                                disabled={!canBuy}>
                            Buy {resource.name}
                        </Button>
                    </td>,
                    <td>
                        <Button color="primary"
                                style={canSell ? defaultCursor : notAllowedCursor}
                                onClick={event => this.sellResource(event, resource).bind(this)}
                                disabled={!canSell}>
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
