import React, {Component} from "react";
import {Col, Row} from 'reactstrap';

export default class User extends Component {

    render() {
        let user = this.props.user;

        if (user) {
            return <Col>
                <Row>Name: {user.name}</Row>
                <Row>Location: {user.location}</Row>
                <Row>Coins: {user.coins}</Row>
                {Object.keys(user.resources).map(resource => {
                    return <Row>{resource}: {user.resources[resource]}</Row>
                })}
            </Col>
        } else {
            return <div>Not Found.</div>
        }
    }
}