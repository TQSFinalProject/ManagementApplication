// React
import { React, useState } from 'react';
import { useNavigate } from "react-router-dom";

// Bootstrap
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Toast from 'react-bootstrap/Toast'
import Button from 'react-bootstrap/Button'

// Font Awesome
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faListCheck } from '@fortawesome/free-solid-svg-icons'

// Employee data
import { staff, tasks, stores } from '../App'

// Components
import GeneralNavbar from '../components/GeneralNavbar';
import Pagination from '../components/Pagination';

function secsToMins(secs) {
    var mins = Math.floor(secs / 60);
    var rest_secs = secs % 60;
    return mins.toString().padStart(2, '0') + ":" + rest_secs.toString().padStart(2, '0') + " minutes";
}

function Stores() {

    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const collect = require('collect.js');

    let navigate = useNavigate();

    function handleCallback(page) { // for the pagination buttons

    }

    function getNumberOfTasks(idx) {
        const storeId = stores[idx].id;
        let count = 0;
        for (let task of tasks) {
            if (task.storeId == storeId) {
                count++;
            }
        }
        return count;
    }

    function redirectStoreTasksPage(storeId) {
        navigate('/store_tasks/' + storeId);
    }

    return (
        <>
            <GeneralNavbar />

            <Container>
                <h1 style={{ marginTop: '5%' }}>AFFILIATED STORES</h1>
            </Container>

            <Container style={{ marginTop: '2%' }}>
                <Row>
                    <Col sm={4}>

                        <p><strong>Filters:</strong></p>

                        <label htmlFor="searchAssignedRider" className="inp">
                            <input type="text" id="searchAssignedRider" placeholder="&nbsp;" />
                            <span className="label">Name</span>
                            <span className="focus-bg"></span>
                        </label>

                        <label htmlFor="searchStore" className="inp">
                            <input type="text" id="searchStore" placeholder="&nbsp;" />
                            <span className="label">Address</span>
                            <span className="focus-bg"></span>
                        </label>

                    </Col>
                    <Col sm={8}>
                        <Row className="d-flex justify-content-center">
                            {stores.map((callbackfn, idx) => (
                                <Toast key={"key" + stores[idx].id} style={{ margin: '1%', width: '24vw' }} className="employeeCard">
                                    <Toast.Header closeButton={false}>
                                        <strong className="me-auto">Store #{stores[idx].id} </strong><br />
                                        {/* <a style={{ color: '#06113C', cursor: 'pointer' }} onClick={handleShow}><FontAwesomeIcon icon={faArrowsSpin} /></a> */}
                                    </Toast.Header>
                                    <Toast.Body>
                                        <Container>
                                            <Row>
                                                <Col>
                                                    <span>
                                                        <strong>Name: </strong>{stores[idx].name}<br />
                                                        <strong>Address: </strong>{stores[idx].address}<br />
                                                        <strong>Current tasks: </strong>{ collect(tasks).where('storeId', '=', stores[idx].id).count() }<br />
                                                    </span>
                                                </Col>
                                            </Row>
                                            <Row>
                                                <Col className="d-flex justify-content-center">
                                                    <Button style={{ marginTop: '3%' }} onClick={() => { redirectStoreTasksPage(stores[idx].id) }}><FontAwesomeIcon icon={faListCheck} /></Button>
                                                </Col>
                                            </Row>
                                        </Container>
                                    </Toast.Body>
                                </Toast>
                            ))}
                        </Row>
                        <Row className="d-flex justify-content-center">
                            {/* ? stores per page: TODO: elements per page as pagination input */}
                            <Pagination pageNumber={1} parentCallback={handleCallback} />
                        </Row>
                    </Col>
                </Row>
            </Container>

        </>);
}

export default Stores;