import { render } from '@testing-library/react';
import React from 'react';
import { Form, Button, Modal } from 'react-bootstrap';

function form() {
  return (
    <div className="App">
      <Modal show={true}>
        <Modal.Header>Modal Head Part </Modal.Header>
        <Modal.Body>
          <Form>
            <Form.Group>
              <Form.Label className="label">First Name</Form.Label>
              <Form.Control type="text" placeholder="First Name" />
            </Form.Group>
            <Form.Group controlId="formBasicEmail">
              <Form.Label className="label">Email address</Form.Label>
              <Form.Control type="email" placeholder="Enter email" />
            </Form.Group>
            <Form.Group>
              <Form.Label className="label">Skills</Form.Label>
              <Form.Control as="textarea" rows="10" placeholder="Skills" />
            </Form.Group>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button>Delete</Button>
          <Button>Update</Button>
          <Button>Close</Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
}

export default form;

