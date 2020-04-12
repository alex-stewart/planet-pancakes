import React, {Component} from 'react';
import {Helmet} from "react-helmet";
import {Button, ButtonGroup, Input} from "reactstrap";
import ColorPicker from "./ColorPicker";

const heraldPageStyle = {
    backgroundColor: '#F8ECC2',
    height: 'calc(100% - 56px)',
    paddingTop: '50px',
    paddingLeft: '10%',
    paddingRight: '10%',
    margin: 'auto',
    overflowY: 'scroll'
};

const contentInputStyle = {
  width: '500px'
};

const inputGroupStyle = {
    display: 'flex',
    padding: '0px'
};

const heraldCanvasStyle = {
    padding: '100px'
};

const sizes = {
    1: {name: 'Tiny'},
    2: {name: 'Small'},
    3: {name: 'Medium'},
    4: {name: 'Large'}
};

export default class Herald extends Component {

    constructor(props) {
        super(props);
        this.state = {
            arms: {
                size: 1
            }
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
        context.fillStyle = "#FFFFFF";
        context.clearRect(0, 0, this.refs.canvas.width, this.refs.canvas.height);
        context.fillRect(0, 0, size * 100, size * 100);
    }

    setSize(size) {
        let arms = this.state.arms;
        arms.size = size;
        this.setState({arms: arms});
        this.draw(this.state.canvasContext, size);
    };

    sizeSelectButton(size) {
        return <Button color="dark"
                       onClick={() => this.setSize(size)}>
            {sizes[size].name}
        </Button>
    }

    contentDropdown(size) {
        return <div style={inputGroupStyle}>
            {size}
            <ColorPicker/>
            <Input style={contentInputStyle} type="select" name="select" id="contentShape">
                <option>Blank</option>
                <option>Square</option>
                <option>Circle</option>
                <option>Rectangle</option>
                <option>Triangle</option>
            </Input>
            <ColorPicker/>
        </div>
    }

    render() {
        return (
            <div style={heraldPageStyle}>
                <Helmet>
                    <title>{"PP - Herald"}</title>
                </Helmet>
                <div>
                    <ButtonGroup color="dark">
                        {
                            Object.keys(sizes).map(this.sizeSelectButton.bind(this))
                        }
                    </ButtonGroup>
                </div>
                <div>
                    {
                        Array.from({length: this.state.arms.size}).map(this.contentDropdown.bind(this))
                    }
                </div>

                <canvas ref={"canvas"}
                        style={heraldCanvasStyle}
                        width={1000}
                        height={1000}/>
            </div>
        )
    }
}
