import React, {Component} from 'react';
import {Helmet} from "react-helmet";

export const heraldPageStyle = {
    backgroundColor: '#F8ECC2',
    height: 'calc(100% - 56px)',
    paddingTop: '50px',
    paddingLeft: '10%',
    paddingRight: '10%',
    margin: 'auto',
    overflowY: 'scroll'
};

export default class Herald extends Component {

    constructor(props) {
        super(props);
        this.state = {
            selectedCodexPage: null
        }
    }

    render() {
        return (
            <div style={heraldPageStyle}>
                <Helmet>
                    <title>{"PP - Herald"}</title>
                </Helmet>
            </div>
        )
    }
}
