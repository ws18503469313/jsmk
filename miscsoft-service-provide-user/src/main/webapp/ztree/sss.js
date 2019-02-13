/**
 * 
 */
// 2、zTree回调方法
  // 对得到的json数据进行过滤，加载树的时候执行
   function filter(treeId, parentNode, responseData) {
      var contents = (responseData.content)?responseData.content:responseData;
      // 当第一次加载的时候只显示一级节点，但是要让别人知道对应一级节点下是否有数据，那么就需要设置isParent,为true可以展开，下面有数据。
    // 我这里是由于异步加载数据，只加载根节点以及一级节点。那么一级节点下还有子节点，所以设置isParent显示还有子节点的效果
      if(contents.length >0){
       for(var i= 0 ; i <contents.length;i++){
       // 是否是叶子节点
        var isParent = contents[i].isParent;
        if(isParent == false){
         isParent = true;
        }else{
         isParent = false;
        }
        contents[i].isParent = isParent;
       }
      }
      return contents;
     };
    // 节点异步加载完成后显示形状
   // 加载树的默认显示根节点，以及一级节点，每次异步加载数据都会执行这个方法，也就是有请求都会异步
    function zTreeOnAsyncSuccess(event, treeId, msg) {
      try {
      var treeObj = $.fn.zTree.getZTreeObj("busTree");
       // 获取所有节点
       var nodes = treeObj .getNodes();
       for(var i = 0;i<nodes.length;i++){
       // 展开一级节点
        treeObj .expandNode(nodes[i], true);
       }
      } catch (err) {
       holly.showError("数据异常", err);
      }
    }
    // 设置一个标识，是为了解决点击的时候，执行异步。显示所有的以及节点，以及一级节点下的所有节点
   // 注意：如果你是异步只加载了根节点和一级节点，那么增删改的时候，得到的异步结果，会获取不到该节点的子节点，只有zTreeOnAsyncSuccess方法执行完后，才能够取到子节点的信息。所以在Eclipse中run执行和debugger执行得到不同的结果。因为run运行数据还没接受到。
    /*
	 * var firstAsyncSuccessFlag = 0; function zTreeOnAsyncSuccess(event,
	 * treeId, msg) { if (firstAsyncSuccessFlag == 0 ) { try { var treeObj =
	 * $.fn.zTree.getZTreeObj("busTree"); var selectedNode =
	 * zTree.getSelectedNodes(); //获取整个json节点 var nodes = zTree.getNodes();
	 * //展开根节点下的第一个节点 treeObj.expandNode(nodes[0], true); //把根节点下的所有节点转换为数组 var
	 * childNodes = zTree.transformToArray(nodes[0]); //展开根节点下的第一个节点的第一节节点
	 * treeObj .expandNode(childNodes[1], true); //选中根节点下的第一个节点 treeObj
	 * .selectNode(childNodes[1]); firstAsyncSuccessFlag = 1; } catch (err) {
	 * holly.showError("数据异常", err); } } }
	 */
   // 初始化树
   zTree = $.fn.zTree.init($("#busTree"), setting);
   // 获取树
   var treeObj = $.fn.zTree.getZTreeObj("busTree");
   // 异步刷新整个树，不推荐使用
   treeObj.reAsyncChildNodes(null, "refresh");
   // 新增的时候局部刷新，刷新子节点
   treeObj.reAsyncChildNodes(zTree.getSelectedNodes().childNodes,"refresh");
   // 修改的节点的时候，更改节点信息
   // 1、设置节点值
   treeObj.getSelectedNodes()[0].codeName = $("#treeRootCodeName").val();
   treeObj.getSelectedNodes()[0].codeValue = $("#treeRootCodeValue").val();
   treeObj.SelectedNodes()[0].remark = $("#treeRootRemark").val();
   // 2、更新节点信息
   treeObj.updateNode(zTree.getSelectedNodes()[0]);
    // 搜索功能：
    // 1、根据查询的结果，显示对应树形结构。注意这里只能是对应查找几级节点的信息，还有待优化。
    // 根据code_name模糊查询
    function searchByValue(){
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        // 经过transformToArray转换后是一个Array数组，数组里的每个元素都是object对象，这个对象里包含了node的21个属性。
        var nodes = zTree.transformToArray(zTree.getNodes()[0].children);
        var key=$("#codeName").val();
        // 空格回车符 不做查询 直接显示全部
        if(/^\s*$/.test(key)){
         // updateNodes(false);
         zTree.showNodes(nodes);
         return;
        }
        // 首先隐藏
        zTree.hideNodes(nodes);
        var nodeList=zTree.getNodesByParamFuzzy("CODE_NAME", key); // 模糊匹配
        // 把得到的节点放到filterNodes临时数组中
        var filterNodes=[];
        for(var i=0;i<nodeList.length;i++){
            // 这里只能是级别是2的
             if(nodeList[i].level==2) {
                filterNodes.push(nodeList[i]);
             }
        }
        // 显示
        zTree.showNodes(filterNodes);
        // 取得每一个节点的父节点，进行展示
        for(var i=0;i<filterNodes.length;i++){
             // 主要是找不到父节点的问题
               var parentNode = filterNodes[i].getParentNode();
            // 展开当前节点的父节点下的所有节点
             zTree.expandNode(parentNode, true, false, false);
             // 显示所有父节点
             zTree.showNode(parentNode);         
         }
    }
    // 2、搜索变色功能，但是会显示全部信息，查询到的结果会显示不同的颜色
    // 展开所有节点
    function expand_ztree(treeId){
        var treeObj = $.fn.zTree.getZTreeObj(treeId);
        treeObj.expandAll(true);
    }
    // 节点没有父节点，展开，有父节点隐藏
    function close_ztree(treeId){
        var treeObj = $.fn.zTree.getZTreeObj(treeId);
        var nodes = treeObj.transformToArray(treeObj.getNodes());
        var nodeLength = nodes.length;
        for (var i = 0; i < nodeLength; i++) {
            if (nodes[i].PARENT_ID == null) {
                // 根节点：展开
                treeObj.expandNode(nodes[i], true, true, false);
            } else {
                // 非根节点：收起
                treeObj.expandNode(nodes[i], false, true, false);
            }
        }
    }
    // 查询的onclick时间
    function searchByValue(){
         searchByFlag_ztree("treeDemo","codeName");
    }
    // 得到查询结果，并进行设置样式和展开
    function searchByFlag_ztree(treeId, searchConditionId){
        // <1>.搜索条件
        var searchCondition = $('#' + searchConditionId).val();
        // <2>.得到模糊匹配搜索条件的节点数组集合
        var highlightNodes = new Array();
        if (searchCondition != "") {
            var treeObj = $.fn.zTree.getZTreeObj(treeId);
            // 模糊查询
            highlightNodes = treeObj.getNodesByParamFuzzy("CODE_NAME", searchCondition, null);
        }
        // <3>.高亮显示并展示【指定节点】
        highlightAndExpand_ztree(treeId, highlightNodes);
    }
    // 展开，并显示不同颜色
    function highlightAndExpand_ztree(treeId, highlightNodes){
        var treeObj = $.fn.zTree.getZTreeObj(treeId);
        // <1>. 先把全部节点更新为普通样式
        var treeNodes = treeObj.transformToArray(treeObj.getNodes());
        var nodes = treeObj.transformToArray(treeObj.getNodes()[0].children);
        for (var i = 0; i < treeNodes.length; i++) {
            treeNodes[i].highlight = false;
            treeObj.updateNode(treeNodes[i]);
        }
        // <2>.收起树, 只展开根节点下的一级节点
        close_ztree(treeId);
        // <3>.把指定节点的样式更新为高亮显示，并展开
        if (highlightNodes != null) {
            for (var i = 0; i < highlightNodes.length; i++) {
                  // 高亮显示节点，并展开
                  highlightNodes[i].highlight = true;
                  treeObj.updateNode(highlightNodes[i]);
                  // 高亮显示节点的父节点的父节点....直到根节点，并展示
                  var parentNode = highlightNodes[i].getParentNode();
                  var parentNodes = getParentNodes_ztree(treeId, parentNode);
                  // 展开
                  treeObj.expandNode(parentNodes, true, false, true);
                  treeObj.expandNode(parentNode, true, false, true);
                  // 设置颜色
                  setFontCss_ztree("",highlightNodes[i]);
            }
        }
    }
    // 递归查找父节点
    function getParentNodes_ztree(treeId, node){
        if (node != null) {
            var treeObj = $.fn.zTree.getZTreeObj(treeId);
            var parentNode = node.getParentNode();
            return getParentNodes_ztree(treeId, parentNode);
        } else {
            return node;
        }
    }
    // 设置颜色
    function setFontCss_ztree(treeId, treeNode) {
        if (treeNode.PARENT_ID == null) {
            // 根节点
            return {color:"#333", "font-weight":"bold"};
        } else if (treeNode.isParent == false){
            // 叶子节点
            return (!!treeNode.highlight) ? {color:"#ff0000", "font-weight":"bold"} : {color:"#660099", "font-weight":"normal"};
        } else {
            // 父节点
            return (!!treeNode.highlight) ? {color:"#ff0000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
        }
    }
    // 点击的时候判断节点有没有数据，以便于datadrid是否请求后台刷新
    if(treeNodes.isParent){
        // datagrid刷新
    }else{
        // 提示
    }
  // 获取过滤之外的所有数据
  var filterNodes = treeObj.getNodesByFilter(filters);
  function filters(node) {
        return (node.highlight == true);
   }
  // 获取过滤的所有数据
  treeObj.getNodesByParamFuzzy("CODE_NAME", searchCondition, null);
  // 当焦点放在节点上显示新增Logo
  function addHoverDom(treeId, treeNode) {
      var sObj = $("#" + treeNode.tId + "_span");
      if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
      var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
       + "' title='新增节点' onfocus='this.blur();'></span>";
      sObj.after(addStr);
      var btn = $("#addBtn_"+treeNode.tId);
      if (btn) btn.bind("click", function(){
      // 弹出easy-ui新增对话框
      treeBusinessManager.newTreeDialog();
       return false;
      });
  };
 // 当鼠标不在节点的时候，自动隐藏新增按钮
  function removeHoverDom(treeId, treeNode) {
      $("#addBtn_"+treeNode.tId).unbind().remove();
  };
  // 获取某个节点下的所有节点
 // 需求：当只能在叶子节点上添加数据的时候，但是展现的时候，需要如果点击叶子节点显示叶子节点的数据，点击节点显示该节点下的所有叶子节点的信息。
 // 注意：这里数据用，区分id，是为了去后台进行in查询
  function getAllChildNodes(treeNode) {
        var treeObj = $.fn.zTree.getZTreeObj("sheetTree");// 获取ztree
        var childNodes = treeObj.transformToArray(treeNode);// 把该节点的下的所有数据转换为数组
        var nodes = new Array();
        var j = 0;
        for(i = 0; i < childNodes.length; i++) {
          // 只有保存叶子节点的数据
           if(!childNodes[i].isParent){
                     nodes[j] = childNodes[i].id;
                     // nodes.join(",");
                     j++;
           }
        }
        return nodes;
  }
  // 获取该节点下的所有子节点
  getChildNodes:function(treeNode,result){
        if (treeNode.isParent) {
          var childrenNodes = treeNode.children;
          if (childrenNodes) {
              for (var i = 0; i < childrenNodes.length; i++) {
                  result += ',' + childrenNodes[i].id;
                  result = sheetShareManager.getChildNodes(childrenNodes[i], result);
              }
          }
      }
      return result;
  }
 // 刷新节点
    function refreshNode(id) {
     var treeObj = $.fn.zTree.getZTreeObj("busTree");
        var node = treeObj.getNodeByParam("id", id, null);
        if (node) {
         treeObj.reAsyncChildNodes(node, "refresh");
        } else {
            setTimeout(function() {
                refreshNode(id);
            }, 10);
        }
    }
    // 刷新父节点
    function refreshParentNode(id) {
     var treeObj = $.fn.zTree.getZTreeObj("busTree");
        var node = treeObj.getNodeByParam("id", id, null);
        var pNode = node.getParentNode();
        if (pNode) {
            refreshNode(pNode.id);
        } else {
            refreshNode("0");
        }
    }
// 删除根节点的时候，销毁整棵树
$.fn.zTree.destroy("busTree");
// 修改节点名，判断是否为空，或者超出限制
function zTreeBeforeRename(treeId, treeNode, newName, isCancel) {
    if (newName.length>10||newName.trim().length<=0) {
        toastr.error("填写信息不符合规则!");
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        treeObj.cancelEditName();
        return false;
    }
    return true;
}
function zTreeOnRename(event, treeId, treeNodes, isCancel) {
    // 真正触发的时候为undefiend。触发不成功的时候，为true
    if(isCancel == undefined){
        // 修改操作
    }
}
 // 右键显示新增，修改，删除（与easy-ui整体类似）
 // 右键，填出编辑框
 function zTreeOnRightClick(event, treeId, treeNode) {
  var treeObj = $.fn.zTree.getZTreeObj("busTree");
  // 设置选中右键节点
  treeObj.selectNode(treeNode);
  // 这里可以为为div添加click事件，进行新增，修改，删除操作。
  // 注意：设置选中节点，才能去对应的操作方法中获取选中的几点
  var treeObj = $.fn.zTree.getZTreeObj("busTree");
     var nodes = treeObj.getSelectedNodes();）
   $('#configure-tree-menu').menu('show',{left: event.pageX,top: event.pageY});
 };
 // 删除节点的时候需要注意
 // 由于数据是异步加载的，所以不仅需要进行数据删除，还需要进行逻辑删除。否则数据没有及时的回显，那么树的状态isParent还没改变。
  for (var i=0, l=nodes.length; i < l; i++) {
     treeObj.removeNode(nodes[i]);
  }
 // 在onBeforeRemove中操作，异步的判断
 // 在数据操作成功之后
   var treeObj = $.fn.zTree.getZTreeObj("busTree");
    treeObj.selectNode(treeNode);
    var nodes = treeObj.getSelectedNodes();
    for (var i=0, l=nodes.length; i < l; i++) {
        treeObj.removeNode(nodes[i]);// 默认是false，如果设置为true,那么就会触发onRemove函数
    }
