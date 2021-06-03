import React, { Component } from 'react';
import { Modal, Button } from 'react-bootstrap';
import "./EmployeeCss.css";
import userIcon from "../../assets/images/userIcon.png";

export class EmployeePotentialViewModal extends Component {


    render() {
        return (
            <Modal
                {...this.props}
                size="lg"
                aria-labelledby="contained-modal-title-vcenter"
                centered
            >
                <Modal.Header closeButton>
                    <Modal.Title id="contained-modal-title-vcenter">
                        {this.props.updateison ?
                            <h3>Updated Employee</h3>
                            :
                            <h3>Added Employee</h3>
                        }
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <div className="employeePotentialModalBody">
                        <div className="card h-100">
                            <img className="card-image" alt="img" src={userIcon} />
                            <div className="card-body">
                                <h5 className="card-title"><b> Employee Name: {this.props.name} </b></h5>
                                <h5 className="card-title"><b> Developer Type: {this.props.type} </b></h5>
                                <h5 className="card-title"><b> Developer Grade: {this.props.grade} </b></h5>
                                <h5 className="card-title"><b> Specialized In: {this.props.specialization} </b></h5>
                            </div>
                        </div>
                    </div>
                </Modal.Body>
                <Modal.Footer>
                    <Button onClick={this.props.onHide}>DONE!</Button>
                </Modal.Footer>
            </Modal>
        )
    }
}