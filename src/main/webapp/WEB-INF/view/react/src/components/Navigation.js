import React from 'react';
import {Collapse, Nav, Navbar, NavbarBrand, NavbarToggler, NavItem, NavLink} from 'reactstrap';
import DateAndTime from "./DateAndTime";
import {Link} from "react-router-dom";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {
    faMapMarkedAlt,
    faUser,
    faStore,
    faNewspaper,
    faCalendarAlt,
    faBook, faSignInAlt
} from "@fortawesome/free-solid-svg-icons";

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
        const generateNavItem = function (name, to, icon) {
            return (
                <NavItem>
                    <NavLink to={to}
                             tag={Link}
                             className={"navbar-item"}>
                        <FontAwesomeIcon icon={icon}/>
                        {' ' + name}
                    </NavLink>
                </NavItem>
            )
        };

        const leftNavigation = function () {
            return <Nav className="p2" navbar>
                {generateNavItem("Map", "/map", faMapMarkedAlt)}
                {generateNavItem("News", "/news", faNewspaper)}
                {generateNavItem("Market", "/market", faStore)}
                {generateNavItem("Calendar", "/calendar", faCalendarAlt)}
                {generateNavItem("Codex", "/codex", faBook)}
            </Nav>
        };

        const rightNavigation = function (user) {
            if (user) {
                return (
                    <Nav className="ml-auto" navbar>
                        <NavItem>
                            <DateAndTime/>
                        </NavItem>
                        {generateNavItem(user.name, "/user", faUser)}
                        <NavLink href={"/logout"} className={"navbar-item"}>
                            <FontAwesomeIcon icon={faSignInAlt}/>
                            {' Sign Out'}
                        </NavLink>
                    </Nav>
                )
            } else {
                return (
                    <Nav className="ml-auto" navbar>
                        <NavItem>
                            <DateAndTime/>
                        </NavItem>
                        <NavItem>
                            <NavLink href={"/login"} className={"navbar-item"}>
                                <FontAwesomeIcon icon={faSignInAlt}/>
                                {' Sign In'}
                            </NavLink>
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
