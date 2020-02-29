import React, {Component} from 'react';
import {Route, BrowserRouter} from 'react-router-dom';
import WorldMap from './map/WorldMap';
import Navigation from './Navigation';
import User from './User';
import News from './news/News'
import Market from './market/Market'
import Calendar from './calendar/Calendar'
import Codex from './codex/Codex'
import axios from "axios";

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

    render() {
        return ([
            <BrowserRouter key="browser-router">
                <div className="main-container">
                    <Navigation user={this.state.currentUser}/>
                    <Route exact path="/" component={WorldMap}/>
                    <Route path="/map" component={WorldMap}/>
                    <Route path="/user"
                           render={(props) => <User {...props} user={this.state.currentUser}/>}
                    />
                    <Route path="/news" component={News}/>
                    <Route path="/market"
                           render={(props) => <Market {...props} user={this.state.currentUser}
                                                      updateUser={this.loadCurrentlyLoggedInUser.bind(this)}/>}
                    />
                    <Route path={"/calendar"} component={Calendar}/>
                    <Route path={"/codex"} component={Codex}/>
                </div>
            </BrowserRouter>
        ]);
    }
}
