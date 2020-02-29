import React, {Component} from 'react';
import {Helmet} from "react-helmet";
import CodexCard from "./CodexCard";
import {CODEX_PAGES} from "./CodexPages";
import CardDeck from "reactstrap/es/CardDeck";

export const codexPageStyle = {
    backgroundColor: '#F8ECC2',
    height: 'calc(100% - 56px)',
    paddingTop: '50px',
    paddingLeft: '10%',
    paddingRight: '10%',
    margin: 'auto',
    overflowY: 'scroll'
};

export const cardStyle = {
    flex: 'none',
    boxShadow: '0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19)',
    marginBottom: '30px'
};

export const cardDeckStyle = {
    justifyContent: 'center'
};

export default class Codex extends Component {

    constructor(props) {
        super(props);
        this.state = {
            selectedCodexPage: null
        }
    }

    render() {
        return (
            <div style={codexPageStyle}>
                <Helmet>
                    <title>{"PP - Codex"}</title>
                </Helmet>
                <CardDeck style={cardDeckStyle}>
                    {
                        CODEX_PAGES.map(page => <CodexCard style={cardStyle}
                                                           key={'codex-page-' + page.name}
                                                           codexPage={page}/>)
                    }
                </CardDeck>
            </div>
        )
    }
}
