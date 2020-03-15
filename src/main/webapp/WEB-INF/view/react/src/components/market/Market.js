import React, {Component} from "react";
import axios from "axios";
import commaNumber from "comma-number";
import {Helmet} from "react-helmet";
import MarketTable from "./MarketTable";

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

    render() {
        let user = this.props.user;
        let coinsBar = user ? <div>Coins: {commaNumber(this.props.user.coins)}</div> : null;

        return (
            <div className={"pp-page"}>
                <Helmet>
                    <title>{"PP - Market"}</title>
                </Helmet>
                {coinsBar}
                <MarketTable user={user} resources={this.state.resources}/>
            </div>
        )
    }
}
