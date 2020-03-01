import React, {Component} from 'react';
import axios from "axios";
import NewsPaper from "./NewsPaper";
import {CardDeck} from 'reactstrap';
import Card from "reactstrap/es/Card";
import {Helmet} from "react-helmet";

export const newsPageStyle = {
    backgroundColor: '#F8ECC2',
    height: 'calc(100% - 56px)',
    paddingTop: '50px',
    paddingLeft: '10%',
    paddingRight: '10%',
    margin: 'auto',
    overflowY: 'scroll'
};

export const cardDeckStyle = {
    justifyContent: 'center'
};

export const newspaperCardStyle = {
    flex: 'none',
    boxShadow: '0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19)',
    marginBottom: '30px'
};

export default class News extends Component {

    constructor(props) {
        super(props);
        this.state = {
            papers: []
        }
    };

    componentDidMount() {
        this.getNews();
    }

    getNews() {
        axios.get("/api/news", {
            params: {
                pageNumber: this.state.papers.length,
                pageSize: 20
            }
        })
            .then(
                (result) => {
                    this.setState({
                        papers: this.state.papers.concat(result.data)
                    })
                },
                (error) => {
                    this.setState({
                        error
                    });
                }
            )
    }

    handleScroll = (e) => {
        const bottom = e.target.scrollHeight - e.target.scrollTop === e.target.clientHeight;
        if (bottom) {
            this.getNews();
        }
    };

    render() {
        let papers = this.state.papers || [];

        return <div style={newsPageStyle}
                    onScroll={this.handleScroll}>
            <Helmet>
                <title>{"PP - News"}</title>
            </Helmet>
            <CardDeck style={cardDeckStyle}>
                {papers.map(function (paper) {
                    return <Card style={newspaperCardStyle}
                                 key={'card-' + paper.id}>
                        <NewsPaper paper={paper}
                                   key={paper.id}/>
                    </Card>
                }, this)}
            </CardDeck>
        </div>
    }
}
