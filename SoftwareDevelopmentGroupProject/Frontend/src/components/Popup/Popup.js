import React, {Component} from 'react';
import { Modal, Button } from 'react-bootstrap';

export default class Popup extends Component {


    render() {
        return(
            <Modal
                {...this.props}
                dialogClassName="custom-dialog-pop"
                aria-labelledby="contained-modal-title-vcenter"
                centered
            >
                <Modal.Header closeButton>
                    <Modal.Title id="contained-modal-title-vcenter">
                        Added Employee
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    {this.props.children}
                </Modal.Body>
                <Modal.Footer>
                    <Button onClick={this.props.onHide}>Close</Button>
                </Modal.Footer>
            </Modal>
        )
    }
}