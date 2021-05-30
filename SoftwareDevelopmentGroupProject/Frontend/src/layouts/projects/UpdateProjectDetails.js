import React, { Component } from 'react';
import { Modal, Button } from 'react-bootstrap';
import { AddProject } from './AddProject';
import "./project.css";

export class UpdateProjectDetails extends Component {


    render() {
        return (
            <Modal
                {...this.props}
                dialogClassName="custom-dialog"
                aria-labelledby="contained-modal-title-vcenter"
            >
                <Modal.Header closeButton>
                    <Modal.Title id="contained-modal-title-vcenter">
                        Update Project
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <div className="updateInterface">
                        {this.props.projectdata.map((data) => (
                            <AddProject
                                key={data.id}
                                id={data.id}
                                client={data.client}
                                address={data.address}
                                email={data.email}
                                contactno={data.contactno}
                                solution_description={data.solution_description}
                                date_admitted={data.date_admitted}
                                delivery_date={data.delivery_date}
                                repolink={data.repolink}
                                priority={data.priority}
                                status={data.status}
                                is_html={data.is_html}
                                is_react={data.is_react}
                                is_nodejs={data.is_nodejs}
                                is_css={data.is_css}
                                is_cplus={data.is_cplus}
                                is_oracle={data.is_oracle}
                                is_csharp={data.is_csharp}
                                is_php={data.is_php}
                                is_ruby={data.is_ruby}
                                is_java={data.is_java}
                                is_ml={data.is_ml}
                                is_python={data.is_python}
                                is_mysql={data.is_mysql}
                                is_angular={data.is_angular}
                                is_nosql={data.is_nosql}
                                is_js={data.is_js}
                                is_bootstrap={data.is_bootstrap}
                                updateison={true}
                            />
                        ))}
                    </div>
                </Modal.Body>
                <Modal.Footer>
                    <Button onClick={this.props.onHide}>CLOSE</Button>
                </Modal.Footer>
            </Modal>
        )
    }
}