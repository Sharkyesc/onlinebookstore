import React, { useState, useEffect, useCallback } from 'react';
import { Layout, Divider, Typography, Form, Modal, Row, Col, Button, Input, Pagination } from 'antd';
import { searchBooks, updateBook, deleteBook, addBook } from '../service/book';
import NavBar from '../components/navBar';
import BookList from '../components/bookList';
import BookFormModal from '../components/bookFormModal';

const { Header, Content, Footer } = Layout;
const { Title } = Typography;
const { Search } = Input;

const BookManagePage = () => {
    const [books, setBooks] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [pageSize, setPageSize] = useState(10);
    const [total, setTotal] = useState(0);
    const [searchTerm, setSearchTerm] = useState('');
    const [editingBook, setEditingBook] = useState(null);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [form] = Form.useForm();

 
    const fetchBooks = useCallback(async () => {
        try {
            const result = await searchBooks(searchTerm, currentPage - 1, pageSize);
            setBooks(result.content);
            setTotal(result.totalElements);
        } catch (error) {
            console.error('Failed to fetch books:', error);
        }
    }, [searchTerm, currentPage, pageSize]);


    useEffect(() => {
        fetchBooks();
    }, [fetchBooks]);

    const handleSearch = value => {
        setSearchTerm(value);
        setCurrentPage(1); 
    };
    
    const handlePageChange = (page, size) => {
        setCurrentPage(page);
        setPageSize(size);
    };

    const showEditModal = (book) => {
        setEditingBook(book);
        form.setFieldsValue(book);
        setIsModalOpen(true);
    };

    const showAddModal = () => {
        setEditingBook(null);
        form.resetFields();
        setIsModalOpen(true);
    };

    const showDeleteConfirm = (bookId) => {
        Modal.confirm({
            title: '确认删除本书籍吗？',
            content: '删除后无法恢复。',
            okText: '确认',
            okType: 'danger',
            cancelText: '取消',
            onOk() {
                handleDelete(bookId);
            },
            onCancel() {
                console.log('取消');
            },
        });
    };

    const handleDelete = async (bookId) => {
        await deleteBook(bookId);
        setBooks(books.filter(book => book.id !== bookId));
    };

    const handleOk = async () => {
        const values = form.getFieldsValue();
        if (editingBook) {
            await updateBook(editingBook.id, values);
        } else {
            await addBook(values);
        }
        fetchBooks(); 
        form.resetFields();
        setIsModalOpen(false);
    };

    const handleCancel = () => {
        setIsModalOpen(false);
    };

    return (
        <Layout>
            <Header>
                <NavBar />
            </Header>
            <Content style={{ padding: '0 50px' }}>
                <Title level={2}>书库</Title>
                <Divider />
                <Row gutter={16} style={{ marginBottom: '20px' }}>
                    <Col span={20}>
                        <Search
                            placeholder="请输入书名进行搜索"
                            size='large'
                            onSearch={handleSearch}
                        />
                    </Col>
                    <Col span={4} style={{ textAlign: 'right' }}>
                        <Button type="primary" size='large' style={{ marginRight: 15 }} onClick={showAddModal}>
                            添加新书
                        </Button>
                    </Col>
                </Row>
                <BookList books={books} onEdit={showEditModal} onDelete={showDeleteConfirm} />
                <BookFormModal
                    visible={isModalOpen}
                    onOk={handleOk}
                    onCancel={handleCancel}
                    form={form}
                    editingBook={editingBook}
                />                       
                <Pagination
                    current={currentPage}
                    pageSize={pageSize}
                    total={total}
                    onChange={handlePageChange}
                    showSizeChanger
                    showQuickJumper
                    style={{ marginTop: '20px', textAlign: 'center' }}
                />
            </Content>
            <Footer style={{ textAlign: 'center' }}>©2024</Footer>
        </Layout>
    );
};

export default BookManagePage;
