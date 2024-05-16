import React from 'react';
import NavBar from '../components/navBar'; 
//import AvatarUpload from '../components/avatarUpload';
import { Layout, Input, Button, message, Upload } from 'antd';
import { UploadOutlined } from '@ant-design/icons';

const { Header, Content, Footer } = Layout;
const { TextArea } = Input;

const props = {
    name: 'file',
    action: 'https://660d2bd96ddfa2943b33731c.mockapi.io/api/upload',
    headers: {
      authorization: 'authorization-text',
    },
    onChange(info) {
      if (info.file.status !== 'uploading') {
        console.log(info.file, info.fileList);
      }
      if (info.file.status === 'done') {
        message.success(`${info.file.name} file uploaded successfully`);
      } else if (info.file.status === 'error') {
        message.error(`${info.file.name} file upload failed.`);
      }
    },
  };


const UserPage = () => {
    const userInfo = {
        username: '玉皇大帝',
        avatarSrc: 'https://q5.itc.cn/q_70/images03/20240205/9bbcd6c4ff4146b79dc47dd4ff8d7026.jpeg',
    };

    return (
        <Layout className="layout">
            <Header>
                <NavBar username={userInfo.username} avatarSrc={userInfo.avatarSrc} />
            </Header>
            <Content className="user-detail-container" style={{width: '90%', marginLeft: '5%'}} >
                <h1> 个人中心 </h1>
                <div className="avatar" style={{marginBottom: 10}}>
                    <span>头像：</span>
                    <br />
                    <img src={userInfo.avatarSrc} alt="avatar" style={{ width: 100 }} />
                    <br />
                    <Upload {...props}>
                        <Button icon={<UploadOutlined />}>Upload</Button>
                    </Upload>

                </div>
                <div className="username" style={{marginBottom: 10}}>
                    <span>用户名：</span>
                    <Input value={userInfo.username} style={{width: '50vh'}}/>
                </div>
                <div style={{marginBottom: 10}}>
                    <span>邮箱：</span>
                    <Input placeholder='请输入常用邮箱' style={{width: '60vh'}}/>
                </div>
                <div style={{marginBottom: 10}}>
                    <span>收货地址：</span>
                    <Input placeholder='请输入常用地址' style={{width: '80vh'}}/>
                </div>
                <div>
                    <span>简介：</span>
                    <TextArea rows={4} placeholder='请输入简介' style={{marginTop: 5}}/>
                    <br />
                    <br />
                </div>
                
                <div className="button-container" style={{ display: 'flex', justifyContent: 'space-around' }}>
                    <Button type="primary" style={{ marginRight: 10, backgroundColor: '#000000', color: 'white' }}>保存修改</Button>
                    <Button type="primary" onClick={() =>window.location.href = '/home'} style={{ backgroundColor: '#000000', color: 'white' }}>返回首页</Button>
                </div>

            </Content>
            <Footer style={{ textAlign: 'center' }}>©2024</Footer>
        </Layout>
    );
};

export default UserPage;



