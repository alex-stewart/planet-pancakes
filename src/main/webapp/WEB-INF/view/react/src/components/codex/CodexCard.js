import React, {Component} from 'react';
import {Card, CardImg, CardHeader} from "reactstrap";
import Lightbox from "react-image-lightbox";
import "react-image-lightbox/style.css";

const codexCardStyle = {
    backgroundColor: '#FFFFFF',
    height: '350px',
    width: '300px',
    overflow: 'hidden',
    marginBottom: '20px',
    flex: 'none'
};

const imageStyle = {
    padding: '10px'
};

export default class CodexCard extends Component {

    constructor(props) {
        super(props);
        this.state = {
            isOpen: false
        }
    }

    render() {
        if (this.props.codexPage) {
            return (
                <Card style={codexCardStyle}>
                    <CardHeader>{this.props.codexPage.name}</CardHeader>
                    <CardImg top width={'100%'}
                             src={require('./files/' + this.props.codexPage.image)}
                             style={imageStyle}
                             alt={this.props.codexPage.name}
                             onClick={() => this.setState({isOpen: true})}/>
                    {
                        this.state.isOpen && <Lightbox mainSrc={require('./files/' + this.props.codexPage.image)}
                                                       onCloseRequest={() => this.setState({isOpen: false})}/>
                    }
                </Card>
            )
        } else {
            return null
        }
    }
}