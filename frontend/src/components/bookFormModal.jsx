import React, { useState } from 'react';
import { Modal, Form, Input } from 'antd';
import { Upload, Button, message } from 'antd';
import { UploadOutlined } from '@ant-design/icons';
import { uploadBookCover } from '../service/book.js';


const BookFormModal = ({ visible, onOk, onCancel, form, editingBook }) => {

    const handleBeforeUpload = async (file) => {
      const isJpg = file.type === 'image/jpeg';
      if (!isJpg) {
        message.error('只支持JPG文件');
        return false;
      }
      
      const res = await uploadBookCover(editingBook.id, file);
      if (res.ok) {
        message.success(`文件上传成功`);
      } else {
        message.error('文件上传失败');
      }
      
      return false; 
    };
  
    const uploadProps = {
      name: 'file',
      accept: 'image/jpeg',
      customRequest: ({ file }) => {
        handleBeforeUpload(file);
      },
    };

    return (
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
                <Form.Item name="coverSrc" label="封面" rules={[{ required: true, message: '请上传封面（仅支持jpg文件）' }]}>              
                    <Upload {...uploadProps}>
                        <Button icon={<UploadOutlined />}>上传JPG文件</Button>
                    </Upload>
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
};

export default BookFormModal;


