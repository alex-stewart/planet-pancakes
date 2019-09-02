import React from 'react';
import {Collapse, Navbar, NavbarToggler, NavbarBrand, Nav, NavItem, NavLink} from 'reactstrap';

export default class Example extends React.Component {
    constructor(props) {
        super(props);

        this.toggle = this.toggle.bind(this);
        this.state = {
            isOpen: false
        };
    }

    toggle() {
        this.setState({
            isOpen: !this.state.isOpen
        });
    }

    render() {

        function userNavigation(user) {
            if (user != null) {
                return (
                    <Nav className="ml-auto" navbar>
                        <NavItem>
                            <NavLink href="/user">{user.id}</NavLink>
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
                            <NavLink href="/login">Login</NavLink>
                        </NavItem>
                    </Nav>
                )
            }
        }

        return (
            <Navbar color="dark" dark expand="md">
                <NavbarBrand href="/">Planet Pancakes</NavbarBrand>
                <NavbarToggler onClick={this.toggle}/>
                <Collapse isOpen={this.state.isOpen} navbar>
                    <Nav className="p2" navbar>
                        <NavItem>
                            <NavLink href="/wiki">Wiki</NavLink>
                        </NavItem>
                        <NavItem>
                            <NavLink href="/map">World Map</NavLink>
                        </NavItem>
                    </Nav>
                    {userNavigation(this.props.user)}
                </Collapse>
            </Navbar>
        );
    }
}
