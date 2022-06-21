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

const endpoint_stores = "api/stores";
const endpoint_tasksPerStore = "api/orders/store/";
const endpoint_filterStoreName = "api/stores/name/";
const endpoint_filterStoreAddress = "api/stores/address/";

function Stores() {

    const [stores, setStores] = useState([]);

    const [totalPages, setTotalPages] = useState(0);

    const [tasksPerStore, setTasksPerStore] = useState({});

    const [filterName, setFilterName] = useState("");
    const [filterAddress, setFilterAddress] = useState("");

    let navigate = useNavigate();

    function axiosGet(url, loadPages) {
        axios.get(url).then((response) => {
            setStores(response.data.content);
            if (loadPages) {
                setTotalPages(response.data.totalPages);
            }
        });
    }

    useEffect(() => {
        let url = process.env.REACT_APP_BACKEND_URL + endpoint_stores + "?page=" + 0;
        axiosGet(url, true);
    }, []);

    useEffect(() => {
        for (let store of stores) {
            axios.get(process.env.REACT_APP_BACKEND_URL + endpoint_tasksPerStore + store.id).then((response) => {
                let storeKey = "store" + store.id;
                let newTaskNum = {};
                newTaskNum[storeKey] = response.data.totalElements;

                setTasksPerStore(tasksPerStore => ({
                    ...tasksPerStore,
                    ...newTaskNum
                }));
            });
        }
    }, [stores]);

    function handleCallback(page) {
        if (filterName != "") {
            let url = process.env.REACT_APP_BACKEND_URL + endpoint_filterStoreName + filterName + "?page=" + (page - 1);
            axiosGet(url, true);
        }
        else if (filterAddress != "") {
            let url = process.env.REACT_APP_BACKEND_URL + endpoint_filterStoreAddress + filterAddress + "?page=" + (page - 1);
            axiosGet(url, true);
        }
        else {
            let url = process.env.REACT_APP_BACKEND_URL + endpoint_stores + "?page=" + (page - 1);
            axiosGet(url, true);
        }
    }

    function redirectStoreTasksPage(storeId) {
        navigate('/store_tasks/' + storeId);
    }

    function filterStores() {

        if (filterName != "") {
            let url = process.env.REACT_APP_BACKEND_URL + endpoint_filterStoreName + filterName + "?page=" + 0;
            axiosGet(url, true);
        }
        else if (filterAddress != "") {
            let url = process.env.REACT_APP_BACKEND_URL + endpoint_filterStoreAddress + filterAddress + "?page=" + 0;
            axiosGet(url, true);
        }
        else {
            let url = process.env.REACT_APP_BACKEND_URL + endpoint_stores + "?page=" + 0;
            axiosGet(url, true);
        }
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

                        {/* /stores/name/{storeName} */}
                        <label htmlFor="searchStoreName" className="inp">
                            <input type="text" id="searchStoreName" placeholder="&nbsp;"
                                onChange={e => {
                                    setFilterName(e.target.value)
                                    setFilterAddress("");
                                    document.getElementById("searchStoreAddress").value = "";
                                }} />
                            <span className="label">Name</span>
                            <span className="focus-bg"></span>
                        </label>

                        {/* /stores/address/{storeAddress} */}
                        <label htmlFor="searchStoreAddress" className="inp">
                            <input type="text" id="searchStoreAddress" placeholder="&nbsp;"
                                onChange={e => {
                                    setFilterAddress(e.target.value)
                                    setFilterName("");
                                    document.getElementById("searchStoreName").value = "";
                                }} />
                            <span className="label">Address</span>
                            <span className="focus-bg"></span>
                        </label>

                        <div className='text-center'>
                            <Button className='submitBtn' onClick={() => { filterStores() }}>Search</Button>
                        </div>

                    </Col>
                    <Col sm={8}>
                        {stores.length == 0 ?
                            <h5 style={{ textAlign: 'center' }}>There are no affiliated stores.</h5>
                            :
                            <>
                                <Row className="d-flex justify-content-center">
                                    {stores.map((callbackfn, idx) => (
                                        <Toast key={"key" + stores[idx].id} style={{ margin: '1%', width: '22vw' }} className="employeeCard">
                                            <Toast.Header closeButton={false}>
                                                <strong className="me-auto">Store #{stores[idx].id} </strong><br />
                                            </Toast.Header>
                                            <Toast.Body>
                                                <Container>
                                                    <Row>
                                                        <Col>
                                                            <span>
                                                                <strong>Name: </strong>{stores[idx].storeName}<br />
                                                                <strong>Address: </strong>{stores[idx].storeAddress}<br />
                                                                <strong>Shipping tax: </strong>{stores[idx].shippingTax}%<br />
                                                                <strong>Current tasks: </strong>{tasksPerStore["store" + stores[idx].id]}<br />
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
                            </>
                        }
                    </Col>
                </Row>
            </Container>

        </>);
}

export default Stores;