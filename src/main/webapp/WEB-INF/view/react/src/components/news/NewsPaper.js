import React, {Component} from 'react';
import {Col, Row} from 'reactstrap';
import {GameDate} from "../../util/GameDate";

const secondaryHeadlineStyle = {
    marginTop: '20px'
};

const newspaperImageStyle = {
    display: 'block',
    margin: 'auto',
    objectFit: 'cover',
    width: '100%',
    height: '100%'
};

const newspaperHeadlineStyle = {
    textAlign: 'justify'
};

const newspaperBodyStyle = {
    backgroundColor: '#FFFFFF',
    padding: '20px',
    marginBottom: '100px',
    whiteSpace: 'pre-line',
    boxShadow: '0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19)',
    fontFamily: '"Times New Roman", Times, serif'
};

export default class NewsPaper extends Component {

    render() {
        let paper = this.props.paper;
        
        let newspaperTitle = paper.newspaperTitle;
        let dividerStyle = {
            width: '100%',
            color: '#FFFFFF',
            backgroundColor: paper.newspaperColour
        };

        let headline = paper.headline;
        let headlineStory = paper.headlineStory;
        let imageUrl = paper.imageUrl;
        let secondaryHeadlines = paper.secondaryHeadlines || [];
        let date = GameDate.fromTimestamp(paper.publishDay);

        return <div style={newspaperBodyStyle}
                    className={"container"}>
            <div className={"display-1 text-center"}>
                {newspaperTitle}
            </div>
            <div className={"text-center"}
                 style={dividerStyle}>
                {date.toStringWithoutTime()}
            </div>
            <div style={newspaperHeadlineStyle}
                 className={"display-1 text-center"}>
                {headline}
            </div>
            <Row>
                <Col>
                    <img style={newspaperImageStyle}
                         src={imageUrl}
                         alt={"newspaper-image-" + paper.id}/>
                </Col>
                <Col>
                    {headlineStory}
                </Col>
            </Row>
            <div style={secondaryHeadlineStyle}
                 className={"h4"}>
                {secondaryHeadlines.map(function (headline) {
                    return <span key={headline}>○ {headline} </span>
                }, this)} ○
            </div>
        </div>
    }
}