import "bootstrap/dist/css/bootstrap.min.css";
import { Form, Button, InputGroup, Col } from "react-bootstrap";
import { Formik } from 'formik';
import * as yup from 'yup';
import "../email/email.css";
import axios from "axios";



const schema = yup.object().shape({
    from: yup.string().required(),
    to: yup.string().email().required(),
    subject: yup.string().required(),
});
function updateEmployee(values) {
    const token = localStorage.getItem("token");
    const config = {
        headers: { Authorization: `Token ${token}` },
    };
    //console.log(this.state.employeeParams);
    axios
        .put("http://127.0.0.1:8000/email/send/", values, config)
        .then((response) => {
            if (response.data != null) {
                console.log(response.data);
            }
        });
    console.log(values);
}
export default function FormExample() {

    return (
        <div className="email">
            <Formik
                validationSchema={schema}
                onSubmit={values => updateEmployee(values)}
                initialValues={{
                    from: 'Inertia',
                    to: '',
                    subject: '',
                    message: '',
                }}
            >
                {({
                    handleSubmit,
                    handleChange,
                    handleBlur,
                    values,
                    touched,
                    isValid,
                    errors,
                }) => (
                    <Form noValidate onSubmit={handleSubmit}>
                        <Form.Row>
                            <Form.Group as={Col} md="4" controlId="validationFormik101">
                                <Form.Label>From:</Form.Label>
                                <Form.Control
                                    type="text"
                                    name="from"
                                    value={values.from}
                                    readOnly
                                />
                                <Form.Control.Feedback tooltip>Looks good!</Form.Control.Feedback>
                            </Form.Group>
                            <Form.Group as={Col} md="4" controlId="validationFormik102">
                                <Form.Label>To:</Form.Label>
                                <Form.Control
                                    type="text"
                                    value={values.to}
                                    placeholder="name@example.com"
                                    onChange={handleChange}
                                    name="to"
                                    isInvalid={!!errors.to}
                                />

                                <Form.Control.Feedback tooltip>Looks good!</Form.Control.Feedback>
                            </Form.Group>

                        </Form.Row>
                        <Form.Row>
                            <Form.Group as={Col} md="4" controlId="validationFormikUsername2">
                                <Form.Label>Subject</Form.Label>
                                <Form.Control
                                    type="text"
                                    placeholder="Subject"
                                    name="subject"
                                    value={values.subject}
                                    onChange={handleChange}
                                    isInvalid={!!errors.subject}
                                />
                            </Form.Group>


                        </Form.Row>
                        <Form.Group controlId="exampleForm.ControlTextarea1">
                            <Form.Label>Message:</Form.Label>
                            <Form.Control as="textarea" rows={6} md="4" name="message" onChange={handleChange}
                                value={values.message} />

                        </Form.Group>

                        <Button type="submit">Send Email</Button>
                    </Form>
                )}
            </Formik>
        </div>
    );
}