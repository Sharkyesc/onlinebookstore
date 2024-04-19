import { ConfigProvider, theme } from 'antd';
import AppRouter from './components/router';

function App() {
  return (
    <ConfigProvider theme={theme}>
      <AppRouter />
    </ConfigProvider>
  );
}
export default App;
