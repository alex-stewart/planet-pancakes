import React, {Component} from 'react';
import {GameDate} from "../../util/GameDate";

const newspaperTitleStyle = {
    fontSize: '30px'
};

const newspaperContentStyle = {
    textAlign: 'justify',
    padding: '20px',
    lineHeight: '1.1'
};

const newspaperStyle = {
    backgroundColor: '#FFFFFF',
    whiteSpace: 'pre-line',
    fontFamily: '"Times New Roman", Times, serif',
    height: '500px',
    width: '300px',
    overflow: 'hidden',
    marginBottom: '20px'
};

const paddingStyle = {
    color: '#CCCCCC'
};

const divider = "▆▆▆▆▆▆▆▆▆▆▆▆▆▆▆▆▆";

export default class NewsPaper extends Component {

    headlineDividerLines(lines) {
        let content = "";
        for (let i = 0; i < lines; i++) {
            content = content.concat(divider);
        }
        return <div style={paddingStyle}>{content}</div>
    }

    generateHeadline(headlineContent) {
        return <div>
            <div className={"fill-dots"} key={headlineContent}>{headlineContent}</div>
            {this.headlineDividerLines(3)}
        </div>
    }

    render() {
        let paper = this.props.paper;

        let newspaperTitle = paper.newspaperTitle;
        let dividerStyle = {
            width: '100%',
            color: '#FFFFFF',
            backgroundColor: paper.newspaperColour
        };

        let headlines = paper.headlines || [];
        let date = GameDate.fromTimestamp(paper.publishDay);

        return <div style={newspaperStyle}>
            <div style={newspaperTitleStyle}
                 className={"text-center"}>
                {newspaperTitle}
            </div>
            <div className={"text-center"}
                 style={dividerStyle}>
                {date.toStringWithoutTime()}
            </div>
            <div style={newspaperContentStyle}>
                {
                    headlines.map(headline => this.generateHeadline(headline))
                }
                {this.headlineDividerLines(50)}
            </div>
        </div>
    }
}
