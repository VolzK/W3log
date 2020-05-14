<template>
  <div class="app-container">
    <!--搜索-->
    <div class="filter-container">
      <el-input
        v-model="listQuery.title"
        placeholder="请输入标题"
        style="width: 200px;"
        class="filter-item"
        @keyup.enter.native="handleFilter"
      />

      <el-button type="primary" icon="el-icon-search" @click="handleFilter">搜索</el-button>

      <el-button
        class="filter-item"
        style="margin-left: 10px;"
        type="primary"
        icon="el-icon-edit"
        @click="handleCreate"
      >添加</el-button>
    </div>

    <!--表单-->
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column align="center" label="ID" width="200">
        <template slot-scope="scope">{{ scope.row.id }}</template>
      </el-table-column>

      <el-table-column label="菜单" width="130" align="center">
        <template slot-scope="scope">{{ scope.row.name }}</template>
      </el-table-column>

      <el-table-column label="配置图标" width="400" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.icon }}</span>
        </template>
      </el-table-column>

      <el-table-column label="状态" width="150" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status | statusFilter">{{ scope.row.status | formatStatus }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column label="权重" width="150" align="center">
        <template slot-scope="scope">{{ scope.row.weight }}</template>
      </el-table-column>

      <!--编辑-->
      <el-table-column label="操作" align="center" width="250" class-name="small-padding fixed-width">
        <template slot-scope="{row,$index}">
          <el-button type="primary" size="mini" @click="handleUpdate(row)">编辑</el-button>

          <el-button size="mini" type="danger" @click="handleDelete(row,$index)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 修改弹出框Dialog -->
    <el-dialog title="新增" :visible.sync="dialogCreateFormVisible">
      <el-form
        ref="createForm"
        :model="channel"
        :rules="rules"
        label-position="left"
        label-width="70px"
        style="width: 400px; margin-left:50px;"
      >
        <el-form-item label="菜单" prop="name">
          <el-input v-model="channel.name" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="配置图标" prop="icon">
          <el-input v-model="channel.icon" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="channel.status">
            <el-radio :label="1">显示</el-radio>
            <el-radio :label="0">隐藏</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="权重" prop="weight">
          <el-input v-model="channel.weight" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogCreateFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="createChannel('createForm')">添 加</el-button>
      </div>
    </el-dialog>

    <!-- 修改弹出框Dialog -->
    <el-dialog title="修改" :visible.sync="dialogUpdateFormVisible">
      <el-form
        ref="updateForm"
        :model="channel"
        :rules="rules"
        label-position="left"
        label-width="70px"
        style="width: 400px; margin-left:50px;"
      >
        <el-form-item label="ID" prop="id">
          <el-input v-model="channel.id" disabled autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="菜单" prop="name">
          <el-input v-model="channel.name" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="配置图标" prop="icon">
          <el-input v-model="channel.icon" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="channel.status">
            <el-radio :label="1">显示</el-radio>
            <el-radio :label="0">隐藏</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="权重" prop="weight">
          <el-input v-model="channel.weight" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogUpdateFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateChannel('updateForm')">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  createChannel,
  fetchChannelById,
  updateChannel,
  deleteChannel,
  fetchList
} from "@/api/channel";

export default {
  filters: {
    // el-tag类型转换
    statusFilter(status) {
      const statusMap = {
        1: "primary",
        0: "warning"
      };
      return statusMap[status];
    },
    // 状态显示转换
    formatStatus(status) {
      const statusMap = {
        1: "展示中",
        0: "已隐藏"
      };
      return statusMap[status];
    }
  },
  data() {
    return {
      channel: {},
      rules: {
        name: [
          { required: true, message: "请输入菜单名称", trigger: "blur" },
          { min: 1, max: 20, message: "长度在 1-20 个字符", trigger: "blur" }
        ]
      },
      list: [],
      listLoading: true,

      tableKey: 0,
      total: 0,
      listQuery: {
        page: 1,
        limit: 20,
        importance: undefined,
        title: undefined,
        type: undefined,
        sort: "+id"
      },
      dialogUpdateFormVisible: false,
      dialogCreateFormVisible: false
    };
  },
  created() {
    this.fetchData();
  },
  methods: {
    // 列表
    fetchData: function() {
      this.listLoading = true;
      // 判空
      if (
        this.listQuery.title == null ||
        this.listQuery.title == "" ||
        this.listQuery.title == "undefined"
      ) {
        this.listQuery.title = "";
      }
      fetchList(this.listQuery.title).then(response => {
        this.list = response.data;

        // Just to simulate the time of the request
        setTimeout(() => {
          this.listLoading = false;
        }, 0.1 * 1000);
      });
    },
    // 查询
    handleFilter() {
      this.listQuery.page = 1;
      this.fetchData();
    },
    // 删除
    handleDelete: function(row, index) {
      deleteChannel(row.id).then(response => {
        if (20000 === response.code) {
          this.$message({
            message: "删除成功!",
            type: "success"
          });
          this.list.splice(index, 1);
        } else {
          this.$message({
            message: "删除失败!",
            type: "error"
          });
        }
      });
    },
    resetTemp() {
      this.channel = {
        id: "",
        name: "",
        icon: "",
        weight: "",
        status: ""
      };
    },
    // 新增
    handleCreate: function(row) {
      this.resetTemp();
      this.dialogCreateFormVisible = true;
      this.$nextTick(() => {
        this.$refs["createForm"].clearValidate();
      });
      // this.$router.push("/channel/add/");
    },
    // 新增菜单 todo 添加后ID不刷新
    createChannel(channel) {
      this.$refs[channel].validate(valid => {
        if (valid) {
          createChannel(this.channel).then(response => {
            if (20000 === response.code) {
              // this.list.unshift(this.channel);
              this.dialogCreateFormVisible = false;
              this.fetchData(); // 再次请求刷新ID
              this.$message({
                message: "添加成功!",
                type: "success"
              });
            } else {
              this.$message({
                message: response.message,
                type: "error"
              });
            }
          });
        } else {
          this.$message({
            message: "错误的提交",
            type: "error"
          });
          return false;
        }
      });
    },

    // 修改
    handleUpdate: function(row) {
      fetchChannelById(row.id).then(response => {
        this.channel = response.data;
      });
      this.dialogUpdateFormVisible = true;
    },

    // 更改
    updateChannel: function(channel) {
      // 获取Dialog数据
      const tempData = Object.assign({}, this.channel);
      this.$refs["updateForm"].validate(valid => {
        if (valid) {
          updateChannel(tempData).then(response => {
            if (20000 === response.code) {
              const index = this.list.findIndex(v => v.id === this.channel.id);
              this.list.splice(index, 1, this.channel);
              this.dialogUpdateFormVisible = false;
              this.$notify({
                title: "成功",
                message: "更新成功",
                type: "success",
                duration: 1000
              });
            } else {
              this.$notify({
                title: "错误",
                message: "更新失败",
                type: "error",
                duration: 1000
              });
            }
          });
        }
      });
    }
  }
};
</script>

<style scoped>
.filter-container {
  padding-bottom: 10px;
}
</style>
