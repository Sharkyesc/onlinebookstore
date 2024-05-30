import { PREFIX, getJson } from './common';

export async function getBookStatistics(startDate, endDate) {
    const url = new URL(`${PREFIX}/orders/statistics`);
    if (startDate) url.searchParams.append('startDate', startDate);
    if (endDate) url.searchParams.append('endDate', endDate);
    let res;
    try {
        res = await getJson(url);
    } catch (error) {
        console.error('获取书籍统计信息时出错:', error);
        res = [];
    }
    return res;
}
