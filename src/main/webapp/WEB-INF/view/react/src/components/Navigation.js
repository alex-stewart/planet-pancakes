import React from 'react';
import {Collapse, Nav, Navbar, NavbarBrand, NavbarToggler, NavItem, NavLink} from 'reactstrap';
import DateAndTime from "./DateAndTime";

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
        const leftNavigation = function () {
            return <Nav className="p2" navbar>
                <NavItem>
                    <NavLink href="/map">Map</NavLink>
                </NavItem>
                <NavItem>
                    <NavLink href="/news">News</NavLink>
                </NavItem>
                <NavItem>
                    <NavLink href="/market">Market</NavLink>
                </NavItem>
            </Nav>
        };

        const rightNavigation = function (user) {
            if (user) {
                return <Nav className="ml-auto" navbar>
                    <NavItem>
                        <DateAndTime/>
                    </NavItem>
                    <NavItem>
                        <NavLink href="/user">{user.name}</NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink href="/logout">Logout</NavLink>
                    </NavItem>
                </Nav>
            } else {
                return <Nav className="ml-auto" navbar>,
                    <NavItem>
                        <DateAndTime/>
                    </NavItem>
                    <NavItem>
                        <NavLink href="/login">Login</NavLink>
                    </NavItem>
                </Nav>
            }
        };

        return (
            <Navbar color="dark" dark expand="md">
                <NavbarBrand href="/">Planet Pancakes</NavbarBrand>
                <NavbarToggler onClick={this.toggleNav}/>
                <Collapse isOpen={this.state.navIsOpen} navbar>
                    {leftNavigation()}
                    {rightNavigation(this.props.user)}
                </Collapse>
            </Navbar>
        );
    }
}
