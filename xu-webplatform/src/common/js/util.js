export function export2Excel(columns,list){
    require.ensure([], () => {
        const { export_json_to_excel } = require('@/excel/Export2Excel');
        let tHeader = []
        let filterVal = []
        // console.log(columns)
        if(!columns){
            return;
        }
        columns.forEach(item =>{
            tHeader.push(item.title)
            filterVal.push(item.key)
        })
        const data = list.map(v => filterVal.map(j => v[j]))
        export_json_to_excel(tHeader, data, '数据列表');
    })
}