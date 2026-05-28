# BombGPT 后端接口说明

## 项目简介

BombGPT 是一个基于 SpringBoot + MySQL + DeepSeek API 的校园生活 AI 问答系统。

后端负责：

- 校园知识库检索
- 调用 DeepSeek API
- 返回 AI 回答
- 提供 RESTful 接口供前端调用

---

# 后端公网地址

http://28deee9d.r2.cpolar.top

---

# 接口统一返回格式

所有接口统一返回 JSON：

```json
{
  "code": 200,
  "msg": "success",
  "data": {}
}
```

字段说明：

| 字段 | 类型 | 说明 |
|---|---|---|
| code | Integer | 状态码 |
| msg | String | 返回消息 |
| data | Object | 实际数据 |

---

# 1. AI 问答接口

## 接口地址

GET /question/ask

## 完整请求示例

```text
http://28deee9d.r2.cpolar.top/question/ask?question=校园卡丢了怎么办
```

---

## 请求参数

| 参数名 | 类型 | 必填 | 说明 |
|---|---|---|---|
| question | String | 是 | 用户输入的问题 |

---

## 返回示例

```json
{
  "code": 200,
  "msg": "success",
  "data": "校园卡丢失后可以先挂失，再前往相关服务点补办。（根据知识库信息）"
}
```

---

# 2. 知识库列表接口

## 接口地址

GET /knowledge/list

## 完整请求示例

```text
http://28deee9d.r2.cpolar.top/knowledge/list
```

---

## 返回示例

```json
{
  "code": 200,
  "msg": "success",
  "data": [
    {
      "id": 1,
      "question": "大学城校区有哪些食堂？",
      "answer": "大学城校区设有多个食堂……",
      "keywords": "食堂,大学城,餐厅"
    }
  ]
}
```

---

# 3. 知识库搜索接口

## 接口地址

GET /knowledge/search

## 完整请求示例

```text
http://28deee9d.r2.cpolar.top/knowledge/search?keyword=食堂
```

---

## 请求参数

| 参数名 | 类型 | 必填 | 说明 |
|---|---|---|---|
| keyword | String | 是 | 搜索关键词 |

---

## 返回示例

```json
{
  "code": 200,
  "msg": "success",
  "data": [
    {
      "id": 1,
      "question": "大学城校区有哪些食堂？",
      "answer": "大学城校区设有多个食堂……",
      "keywords": "食堂,大学城,餐厅"
    }
  ]
}
```

---

# 前端调用示例

```javascript
async function askQuestion(question) {

    const res = await fetch(
        "http://28deee9d.r2.cpolar.top/question/ask?question="
        + encodeURIComponent(question)
    );

    const result = await res.json();

    console.log(result.data);
}
```

---

# 后端技术栈

- SpringBoot
- MyBatis
- MySQL
- DeepSeek API
- RESTful API
- Maven

---

# 数据库说明

数据库名称：

```text
campus_helper
```

核心数据表：

| 表名 | 说明 |
|---|---|
| category | 分类表 |
| knowledge | 校园知识库 |
| knowledge_source | 知识来源表 |

---

# 注意事项

- 后端运行依赖 MySQL 数据库
- DeepSeek API Key 使用环境变量读取
- 前端不可直接获取 API Key
- 后端需保持运行状态，公网接口才能访问