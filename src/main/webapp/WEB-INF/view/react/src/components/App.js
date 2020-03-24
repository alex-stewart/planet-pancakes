import React, {Component} from 'react';
import {BrowserRouter, Route, Switch} from 'react-router-dom';
import WorldMap from './map/WorldMap';
import User from './User';
import News from './news/News'
import Market from './market/Market'
import Calendar from './calendar/Calendar'
import Codex from './codex/Codex'
import PanCan from './pancan/PanCan'
import axios from "axios";
import Navigation from "./Navigation";

export default class App extends Component {

    constructor(props) {
        super(props);
        this.state = {
            currentUser: null,
            authenticated: false,
            loading: false
        };
    }

    loadCurrentlyLoggedInUser() {
        this.setState({
            loading: true
        });

        axios.get("/api/users/me")
            .then(
                (result) => {
                    this.setState({
                        currentUser: result.data,
                        authenticated: true,
                        loading: false
                    });
                },
                () => {
                    this.setState({
                        loading: false
                    });
                }
            )
    }

    handleLogout() {
        this.setState({
            authenticated: false,
            currentUser: null
        });
    }

    componentDidMount() {
        this.loadCurrentlyLoggedInUser();
    }

    componentWithNav(path, component, user) {
        return <Route exact path={path}>
            <div className="main-container">
                <Navigation user={user}/>
                {component}
            </div>
        </Route>
    }

    render() {
        let user = this.state.currentUser;

        return (
            <BrowserRouter key="browser-router">
                <Switch>
                    {this.componentWithNav("/", <WorldMap/>, user)}
                    {this.componentWithNav("/map", <WorldMap/>, user)}
                    {this.componentWithNav("/user", <User user={user}/>, user)}
                    {this.componentWithNav("/news", <News/>, user)}
                    {this.componentWithNav("/market", <Market user={user}
                                                              updateUser={this.loadCurrentlyLoggedInUser.bind(this)}/>, user)}
                    {this.componentWithNav("/calendar", <Calendar/>, user)}
                    {this.componentWithNav("/codex", <Codex/>, user)}
                    <Route path={"/pp"} component={PanCan}/>
                </Switch>
            </BrowserRouter>
        );
    }
}
