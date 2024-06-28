import React from 'react';
import { Modal, Form, Input } from 'antd';

const BookFormModal = ({ visible, onOk, onCancel, form, editingBook }) => (
    <Modal
        title={editingBook ? "编辑图书" : "添加图书"}
        open={visible}
        onOk={onOk}
        onCancel={onCancel}
    >
        <Form form={form} layout="vertical">
            <Form.Item name="title" label="书名" rules={[{ required: true, message: '请输入书名' }]}>
                <Input />
            </Form.Item>
            <Form.Item name="author" label="作者" rules={[{ required: true, message: '请输入作者' }]}>
                <Input />
            </Form.Item>
            <Form.Item name="coverSrc" label="封面URL" rules={[{ required: true, message: '请输入封面URL' }]}>
                <Input />
            </Form.Item>
            <Form.Item name="isbn" label="ISBN编号" rules={[{ required: true, message: '请输入ISBN编号' }]}>
                <Input />
            </Form.Item>
            {!editingBook && <Form.Item name="price" label="价格（分）" rules={[{ required: true, message: '请输入价格' }]}>
                <Input type="number" />
            </Form.Item> }
            <Form.Item name="stocks" label="库存量" rules={[{ required: true, message: '请输入库存量' }]}>
                <Input type="number" />
            </Form.Item>
            <Form.Item name="description" label="简介">
                <Input.TextArea 
                    autoSize={true}
                />
            </Form.Item>
        </Form>
    </Modal>
);

export default BookFormModal;
