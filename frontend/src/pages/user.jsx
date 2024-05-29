import { React, useState, useEffect } from 'react';
import NavBar from '../components/navBar'; 
//import AvatarUpload from '../components/avatarUpload';
import { Layout, Input, Button, message, Upload } from 'antd';
import { UploadOutlined } from '@ant-design/icons';
import { getUserCompleteInfo, updateUserInfo } from '../service/user';

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
    const [userInfo, setUserInfo] = useState({ username: '', nickname: '', email: '', address: '', phonenumber: '', avatarSrc: '' });

    useEffect(() => {
        const fetchUserInfo = async () => {
            const data = await getUserCompleteInfo();
            console.log(data);
            if (data) {
                setUserInfo(data);
            }
        };
        fetchUserInfo();
    }, []);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setUserInfo((prevInfo) => ({
            ...prevInfo,
            [name]: value,
        }));
    };

    const handleSave = async () => {
        const response = await updateUserInfo(userInfo);
        if (response.success) {
            message.success('用户信息更新成功');
        } else {
            message.error('用户信息更新失败');
        }
    };

    return (
        <Layout className="layout">
            <Header>
                <NavBar />
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
                <div style={{marginBottom: 10}}>
                    <span>用户名：</span>
                    <Input
                        name="username"
                        value={userInfo.username}
                        disabled
                        style={{width: '50vh'}}
                    />
                </div>
                <div style={{marginBottom: 10}}>
                    <span>昵称：</span>
                    <Input name="nickname" value={userInfo.nickname} placeholder='请输入昵称' 
                        onChange={handleInputChange} style={{width: '50vh'}}/>
                </div>
                <div style={{marginBottom: 10}}>
                    <span>邮箱：</span>
                    <Input name="email" value={userInfo.email} placeholder='请输入常用邮箱' 
                        onChange={handleInputChange} style={{width: '60vh'}}/>
                </div>
                <div style={{marginBottom: 10}}>
                    <span>收货地址：</span>
                    <Input name="address" value={userInfo.address} placeholder='请输入常用地址' 
                        onChange={handleInputChange} style={{width: '80vh'}}/>
                </div>
                <div style={{marginBottom: 10}}>
                    <span>联系电话：</span>
                    <Input name="phonenumber" value={userInfo.phonenumber} placeholder='请输入联系电话' 
                        onChange={handleInputChange} style={{width: '80vh'}}/>
                </div>
{/*                 <div>
                    <span>简介：</span>
                    <TextArea rows={4} placeholder='请输入简介' style={{marginTop: 5}}/>
                    <br />
                    <br />
                </div> */}
                
                <div className="button-container" style={{ display: 'flex', justifyContent: 'space-around' }}>
                    <Button type="primary" onClick={handleSave} style={{ marginRight: 10, backgroundColor: '#000000', color: 'white' }}>保存修改</Button>
                    <Button type="primary" onClick={() =>window.location.href = '/home'} style={{ backgroundColor: '#000000', color: 'white' }}>返回首页</Button>
                </div>

            </Content>
            <Footer style={{ textAlign: 'center' }}>©2024</Footer>
        </Layout>
    );
};

export default UserPage;



