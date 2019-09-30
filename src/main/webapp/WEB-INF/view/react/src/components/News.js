import React, {Component} from 'react';
import axios from "axios";
import NewsPaper from "./NewsPaper";

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
                pageNumber: this.state.papers.length
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

        return <div className={"news-page"} onScroll={this.handleScroll}>
            {papers.map(function (paper) {
                return <NewsPaper paper={paper}/>
            }, this)}
        </div>
    }
}