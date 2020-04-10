import React, {Component} from 'react';
import {Helmet} from "react-helmet";
import {Button, ButtonGroup} from "reactstrap";

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
            typeDropdownOpen: false,
            selectedSize: 1
        };
    }

    componentDidMount() {
        let ctx = this.refs.canvas.getContext('2d');
        this.setState({
            canvasContext: ctx
        });
        this.draw(ctx, 1)
    }

    draw(context, size) {
        context.fillStyle= "#FFFFFF";
        context.clearRect(0, 0, this.refs.canvas.width, this.refs.canvas.height);
        context.fillRect(0, 0, size * 100, size * 100);
    }

    setSize(size) {
        this.setState({selectedSize: size});
        this.draw(this.state.canvasContext, size);
    };

    render() {
        return (
            <div style={heraldPageStyle}>
                <Helmet>
                    <title>{"PP - Herald"}</title>
                </Helmet>
                <div>
                    <ButtonGroup color="dark">
                        <Button color="dark"
                                onClick={() => this.setSize(1)}>
                            Small
                        </Button>
                        <Button color="dark"
                                onClick={() => this.setSize(2)}>
                            Medium
                        </Button>
                        <Button color="dark"
                                onClick={() => this.setSize(3)}>
                            Large
                        </Button>
                    </ButtonGroup>
                </div>
                <canvas ref={"canvas"} width={1000} height={1000}/>
            </div>
        )
    }
}
