import React, { Component } from 'react'

// Components
import GeneralNavbar from '../components/GeneralNavbar';

// Bootstrap
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import { Form } from 'react-bootstrap';
import Button from 'react-bootstrap/Button'

export class Login extends Component {
    render() {
        return (
            <>
                <GeneralNavbar />

                <Container>
                    <h1 style={{ marginTop: '5%' }}>LOGIN</h1>
                </Container>

                <Container>
                    <Row className="d-flex justify-content-center">
                        <Col className='align-self-center col-xs-1' align='center'>
                            <Form style={{ marginTop: '3%' }}>
                                <Form.Group style={{ width: '40vw' }}>
                                    <Form.Label htmlFor="searchAssignedRider" className="inp">
                                        <Form.Control type="text" id="searchAssignedRider" placeholder="&nbsp;" />
                                        <span className="label">Username</span>
                                        <span className="focus-bg"></span>
                                    </Form.Label>
                                </Form.Group>
                                <Form.Group style={{ width: '40vw' }}>
                                    <Form.Label htmlFor="searchStore" className="inp">
                                        <Form.Control type="password" id="searchStore" placeholder="&nbsp;" />
                                        <span className="label">Password</span>
                                        <span className="focus-bg"></span>
                                    </Form.Label>
                                </Form.Group>
                                <Button variant="primary" type="submit" style={{ marginTop: '3%' }}>
                                    Submit
                                </Button>
                            </Form>
                        </Col>
                    </Row>
                </Container>
            </>
        )
    }
}

export default Login