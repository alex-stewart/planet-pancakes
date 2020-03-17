import React, {Component} from "react";
import axios from "axios";
import commaNumber from "comma-number";
import {Helmet} from "react-helmet";
import MarketTable from "./MarketTable";
import _ from "lodash";

export default class Market extends Component {

    constructor(props) {
        super(props);
        this.state = {
            resources: []
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

    renderMarketTables(user, resourcesByType, updateUser) {
        let marketTables = [];
        for (const [key, value] of Object.entries(resourcesByType)) {
            let table = this.renderMarketTablesForResourceType(user, value, updateUser, key);
            marketTables.push(table);
        }
        return marketTables;
    }

    renderMarketTablesForResourceType(user, resources, updateUser, resourceType) {
        return <MarketTable user={user}
                            resources={resources}
                            updateUser={updateUser}
                            resourceType={resourceType}/>
    }

    render() {
        let user = this.props.user;
        let coinsBar = user ? <div>Coins: {commaNumber(this.props.user.coins)}</div> : null;
        let resourcesByType = _.groupBy(this.state.resources, 'category');

        return (
            <div className={"pp-page"}>
                <Helmet>
                    <title>{"PP - Market"}</title>
                </Helmet>
                {coinsBar}
                {
                    this.renderMarketTables(this.props.user, resourcesByType)
                }
            </div>
        )
    }
}
