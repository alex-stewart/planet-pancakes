import React from 'react';
import {Collapse, Nav, Navbar, NavbarBrand, NavbarToggler, NavItem, NavLink} from 'reactstrap';
import DateAndTime from "./DateAndTime";
import {Link} from "react-router-dom";

export default class Example extends React.Component {
    constructor(props) {
        super(props);

        this.toggleNav = this.toggleNav.bind(this);
        this.state = {
            navIsOpen: false,
        };
    }

    toggleNav() {
        this.setState({
            navIsOpen: !this.state.navIsOpen
        });
    }

    render() {
        const generateNavItem = function (name, to) {
            return (
                <NavItem>
                    <NavLink to={to}
                             tag={Link}
                             className={"navbar-item"}>
                        {name}
                    </NavLink>
                </NavItem>
            )
        };

        const leftNavigation = function () {
            return <Nav className="p2" navbar>
                {generateNavItem("Map", "/map")}
                {generateNavItem("News", "/news")}
                {generateNavItem("Market", "/market")}
                {generateNavItem("Calendar", "/calendar")}
                {generateNavItem("Codex", "/codex")}
            </Nav>
        };

        const rightNavigation = function (user) {
            if (user) {
                return (
                    <Nav className="ml-auto" navbar>
                        <NavItem>
                            <DateAndTime/>
                        </NavItem>
                        {generateNavItem(user.name, "/user")}
                        <NavLink href={"/logout"} className={"navbar-item"}>Logout</NavLink>
                    </Nav>
                )
            } else {
                return (
                    <Nav className="ml-auto" navbar>
                        <NavItem>
                            <DateAndTime/>
                        </NavItem>
                        <NavItem>
                            <NavLink href={"/login"} className={"navbar-item"}>Login</NavLink>
                        </NavItem>
                    </Nav>
                )
            }
        };

        return (
            <Navbar color="dark" dark expand="md">
                <NavbarBrand className={"navbar-brand"}
                             tag={Link}
                             to="/">
                    PP
                </NavbarBrand>
                <NavbarToggler onClick={this.toggleNav}/>
                <Collapse isOpen={this.state.navIsOpen} navbar>
                    {leftNavigation()}
                    {rightNavigation(this.props.user)}
                </Collapse>
            </Navbar>
        );
    }
}
