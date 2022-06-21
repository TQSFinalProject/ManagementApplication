// React
import { React, useState, useEffect } from 'react';
import { Link } from "react-router-dom";
import axios from "axios";

// Components
import GeneralNavbar from '../components/GeneralNavbar';
import StarRating from '../components/StarRating';
import Pagination from '../components/Pagination';

// Bootstrap
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Toast from 'react-bootstrap/Toast'
import Dropdown from 'react-bootstrap/Dropdown'
import Button from 'react-bootstrap/Button'
import Modal from 'react-bootstrap/Modal'
import Badge from 'react-bootstrap/Badge'

// Font Awesome
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faArrowsSpin } from '@fortawesome/free-solid-svg-icons'

// CSS
import SearchBar from '../components/css/SearchBar.css'

const endpoint_orders = "api/orders";
const endpoint_riders = "api/riders";
const endpoint_stores = "api/stores";

export function secsToMins(secs) {
    var mins = Math.floor(secs / 60);
    var rest_secs = secs % 60;
    return mins.toString().padStart(2, '0') + ":" + rest_secs.toString().padStart(2, '0') + " minutes";
}

function Tasks() {

    const [show, setShow] = useState(false);

    const [tasks, setTasks] = useState([]);

    const [totalPages, setTotalPages] = useState(0);
    const [ridernames, setRidernames] = useState({});
    const [storenames, setStorenames] = useState({});

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    useEffect(() => {
        axios.get(process.env.REACT_APP_BACKEND_URL + endpoint_orders + "?page=" + 0).then((response) => {
            setTasks(response.data.content);
            setTotalPages(response.data.totalPages);
        });

    }, []);

    useEffect(() => {
        
        for (let task of tasks) {
            let riderId = task.riderId;
            let storeId = task.storeId;

            axios.get(process.env.REACT_APP_BACKEND_URL + endpoint_riders + "/" + riderId).then((response) => {
                let riderKey = "rider" + riderId;
                let newRidername = {};
                newRidername[riderKey] = response.data.firstName + " " + response.data.lastName;

                setRidernames(ridernames => ({
                    ...ridernames,
                    ...newRidername
                }));
            });

            axios.get(process.env.REACT_APP_BACKEND_URL + endpoint_stores + "/" + storeId).then((response) => {
                let storeKey = "store" + storeId;
                let newStorename = {};
                newStorename[storeKey] = response.data.storeName;

                setStorenames(storenames => ({
                    ...storenames,
                    ...newStorename
                }));
            });
        }

    }, [tasks]);

    function handleCallback(page) {
        axios.get(process.env.REACT_APP_BACKEND_URL + endpoint_orders + "?page=" + (page-1)).then((response) => {
            setTasks(response.data.content);
        });
    }

    return (
        <>
            <GeneralNavbar />

            <Container>
                <h1 style={{ marginTop: '5%' }}>ALL TASKS</h1>
            </Container>

            <Container style={{ marginTop: '2%' }}>
                {tasks.length == 0 ?
                    <Row>
                        <h5 style={{ textAlign: 'center' }}>There are no orders currently.</h5>
                    </Row>
                    :
                    <Row>
                        <Col sm={4}>

                            <p><strong>Filters:</strong></p>

                            <label htmlFor="searchAssignedRider" className="inp">
                                <input type="text" id="searchAssignedRider" placeholder="&nbsp;" />
                                <span className="label">Assigned rider</span>
                                <span className="focus-bg"></span>
                            </label>

                            <label htmlFor="searchStore" className="inp">
                                <input type="text" id="searchStore" placeholder="&nbsp;" />
                                <span className="label">Store/Restaurant</span>
                                <span className="focus-bg"></span>
                            </label>

                            <Dropdown className='filterDropdown'>
                                <Dropdown.Toggle id="dropdown-basic">
                                    Lateness
                                </Dropdown.Toggle>
                                <Dropdown.Menu>
                                    <Dropdown.Item className="clickable">On time</Dropdown.Item>
                                    <Dropdown.Item className="clickable">Late</Dropdown.Item>
                                    <Dropdown.Item className="clickable">Very late</Dropdown.Item>
                                </Dropdown.Menu>
                            </Dropdown>

                            <Dropdown className='filterDropdown'>
                                <Dropdown.Toggle id="dropdown-basic">
                                    Distance
                                </Dropdown.Toggle>
                                <Dropdown.Menu>
                                    <Dropdown.Item className="clickable">Close</Dropdown.Item>
                                    <Dropdown.Item className="clickable">Average</Dropdown.Item>
                                    <Dropdown.Item className="clickable">Far away</Dropdown.Item>
                                </Dropdown.Menu>
                            </Dropdown>

                        </Col>
                        <Col sm={8}>
                            <Row className="d-flex justify-content-center">
                                {tasks.map((callbackfn, idx) => (
                                    <Toast key={"key" + tasks[idx].id} style={{ margin: '1%', width: '24vw' }} className="employeeCard">
                                        <Toast.Header closeButton={false}>
                                            <strong className="me-auto">Task #{tasks[idx].id} </strong><br />
                                            {tasks[idx].orderStatus == 'Late' ?
                                                <Badge style={{ marginRight: '5%' }} bg="danger">Late</Badge>
                                                :
                                                <></>
                                            }
                                            <a style={{ color: '#06113C', cursor: 'pointer' }} onClick={handleShow}><FontAwesomeIcon icon={faArrowsSpin} /></a>
                                        </Toast.Header>
                                        <Toast.Body>
                                            <Container>
                                                <Row>
                                                    <Col>
                                                        <span>
                                                            <strong>Rider: </strong>{ridernames["rider" + tasks[idx].riderId]}<br />
                                                            <strong>Store: </strong>{storenames["store" + tasks[idx].storeId]}<br />
                                                            <strong>Delivery address: </strong>{tasks[idx].deliveryAddress}<br />
                                                        </span>
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

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Change assigned rider</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Container>
                        <Row className="d-flex justify-content-center">
                            <label htmlFor="changeAssignedRider" className="inp" style={{ width: '100%', margin: '0%', padding: '0%' }}>
                                <input type="text" id="changeAssignedRider" placeholder="&nbsp;" />
                                <span className="label">Assigned rider</span>
                                <span className="focus-bg"></span>
                            </label>
                        </Row>
                        <Row>
                            <p style={{ marginTop: '3%' }}>Suggested:</p>
                            <Col>
                                <Button className='riderSuggestion' style={{ marginRight: '-5%' }}>Afonso Campos</Button>
                            </Col>
                            <Col>
                                <Button className='riderSuggestion'>Miguel Ferreira</Button>
                            </Col>
                            <Col>
                                <Button className='riderSuggestion'>Isabel Rosário</Button>
                            </Col>
                        </Row>
                    </Container>

                </Modal.Body>
            </Modal>
        </>);
}

export default Tasks;