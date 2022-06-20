// React
import { React, useState, useEffect } from 'react';
import { useNavigate } from "react-router-dom";
import axios from "axios";

// Bootstrap
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Toast from 'react-bootstrap/Toast'
import Button from 'react-bootstrap/Button'

// Font Awesome
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faListCheck } from '@fortawesome/free-solid-svg-icons'

// Components
import GeneralNavbar from '../components/GeneralNavbar';
import Pagination from '../components/Pagination';

const endpoint_orders = "api/orders";
const endpoint_riders = "api/riders";
const endpoint_stores = "api/stores";

function secsToMins(secs) {
    var mins = Math.floor(secs / 60);
    var rest_secs = secs % 60;
    return mins.toString().padStart(2, '0') + ":" + rest_secs.toString().padStart(2, '0') + " minutes";
}

function Stores() {

    const [show, setShow] = useState(false);

    const [tasks, setTasks] = useState([]);
    const [stores, setStores] = useState([]);

    const [totalPages, setTotalPages] = useState(0);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const collect = require('collect.js');

    let navigate = useNavigate();

    useEffect(() => {
        axios.get(process.env.REACT_APP_BACKEND_URL + endpoint_stores + "?page=" + 0).then((response) => {
            setStores(response.data.content);
            setTotalPages(response.data.totalPages);
        });
    }, []);

    useEffect(() => {
        // TODO: fetch each store's number of tasks
    }, [stores]);

    function handleCallback(page) { // for the pagination buttons
        axios.get(process.env.REACT_APP_BACKEND_URL + endpoint_stores + "?page=" + (page-1)).then((response) => {
            setStores(response.data.content);
        });
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
                {stores.length == 0 ?
                    <Row>
                        <h5 style={{ textAlign: 'center' }}>There are no affiliated stores.</h5>
                    </Row>
                    :
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
                                    <Toast key={"key" + stores[idx].id} style={{ margin: '1%', width: '22vw' }} className="employeeCard">
                                        <Toast.Header closeButton={false}>
                                            <strong className="me-auto">Store #{stores[idx].id} </strong><br />
                                            {/* <a style={{ color: '#06113C', cursor: 'pointer' }} onClick={handleShow}><FontAwesomeIcon icon={faArrowsSpin} /></a> */}
                                        </Toast.Header>
                                        <Toast.Body>
                                            <Container>
                                                <Row>
                                                    <Col>
                                                        <span>
                                                            <strong>Name: </strong>{stores[idx].storeName}<br />
                                                            <strong>Address: </strong>{stores[idx].storeAddress}<br />
                                                            <strong>Shipping tax: </strong>{stores[idx].shippingTax}%<br />
                                                            <strong>Current tasks: </strong>{collect(tasks).where('storeId', '=', stores[idx].id).count()}<br />
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
                                <Pagination pageNumber={totalPages} parentCallback={handleCallback} />
                            </Row>
                        </Col>
                    </Row>
                }
            </Container>

        </>);
}

export default Stores;