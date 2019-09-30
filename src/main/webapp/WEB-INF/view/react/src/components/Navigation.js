import React from 'react';
import {Collapse, Navbar, NavbarToggler, NavbarBrand, Nav, NavItem, NavLink} from 'reactstrap';
import DateAndTime from "./DateAndTime";

const MILLISECONDS_IN_DAY = 86400000;

export default class Example extends React.Component {
    constructor(props) {
        super(props);

        this.toggle = this.toggle.bind(this);
        this.state = {
            isOpen: false,
            date: Date.now()
        };
    }

    toggle() {
        this.setState({
            isOpen: !this.state.isOpen
        });
    }

    tick() {
        this.setState({
            date: new Date(),
        });
    }

    componentDidMount() {
        this.timerID = setInterval(
            () => this.tick(),
            3600000
        );
    }

    componentWillUnmount() {
        clearInterval(this.timerID);
    }

    render() {
        function userNavigation(user, days) {
            if (user != null) {
                return (
                    <Nav className="ml-auto" navbar>
                        <NavItem>
                            <div className={"navbar-date-time"}><DateAndTime days={days}/></div>
                        </NavItem>
                        <NavItem>
                            <NavLink href="/user">{user.name}</NavLink>
                        </NavItem>
                        <NavItem>
                            <NavLink href="/logout">Logout</NavLink>
                        </NavItem>
                    </Nav>
                )
            } else {
                return (
                    <Nav className="ml-auto" navbar>,
                        <NavItem>
                            <div className={"navbar-date-time"}><DateAndTime days={days}/></div>
                        </NavItem>
                        <NavItem>
                            <NavLink href="/login">Login</NavLink>
                        </NavItem>
                    </Nav>
                )
            }
        }

        let days = Math.ceil(this.state.date / MILLISECONDS_IN_DAY);

        return (
            <Navbar color="dark" dark expand="md">
                <NavbarBrand href="/">Planet Pancakes</NavbarBrand>
                <NavbarToggler onClick={this.toggle}/>
                <Collapse isOpen={this.state.isOpen} navbar>
                    <Nav className="p2" navbar>
                        <NavItem>
                            <NavLink href="/map">World Map</NavLink>
                        </NavItem>
                        <NavItem>
                            <NavLink href="/news">News</NavLink>
                        </NavItem>
                    </Nav>
                    {userNavigation(this.props.user, days)}
                </Collapse>
            </Navbar>
        );
    }
}
