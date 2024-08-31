# Sobat Tani: Food crop health detection app

<p align="center">
  <img src="https://github.com/CharlesD12/Bangkit_Capstone_Project/assets/78160523/1d6a426c-7d54-4584-968c-ce140353cb12" alt="Sobat Tani App">
</p>

# Videos
<iframe width="560" height="315" src="https://www.youtube.com/embed/Ir9clz16Z5w?si=UvW0wXaGKytq2IER" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>

# About
Indonesia, an agrarian country, faces challenges in raising farmers’ technological awareness. To address this, an app has been developed to help farmers detect crop diseases, enabling early treatment and prevention of crop failure. This app serves as a proactive measure against potential future food crises. It represents a digital realization of an idea, aimed at promoting digitalization in Indonesia’s agricultural sector. This app is a tangible effort to help Indonesian farmers overcome current challenges, with the hope of mitigating the impact of agricultural issues and potential food crises.

## Team Members

| ID         | Name                          | Learning Path |
|------------|-------------------------------|---------------|
|M009D4KY3136| Charles Dometian              | ML            |
|M006D4KY2941| Richard Baggio Tan            | ML            |
|M006D4KY2366| Zidhar Akadhistra Muhammad    | ML            |
|C189D4KY0259| Revaldo Relinsyah             | CC            |
|C189D4KX1281| Fanissa Azzahra               | CC            |
|A009D4KY3936| Naufal Hammam Al Mubarok      | MD            |
|A009D4KY3647| Rama Adi Satria               | MD            |

# Machine Learning Documentation
Link dataset :

Rice Dataset : 
https://www.kaggle.com/datasets/shayanriyaz/riceleafs

Corn Dataset : 
https://www.kaggle.com/datasets/smaranjitghose/corn-or-maize-leaf-disease-dataset

Cassava Dataset :
https://www.kaggle.com/datasets/charlesdometian/cassava-plant-diseases

# Mobile Development Documentation

Figma link : https://www.figma.com/proto/bpd7Tm21HTFmTJXUb48QRw/Untitled?node-id=47-338&t=llvfw7fH6cgZtPPM-1

# Cloud Computing Documentation
![image](https://github.com/CharlesD12/Bangkit_Capstone_Project/assets/78160523/2d179cfb-9f3a-4f15-8e3f-aac6e8f94957)


## Table of Contents
## Table of Contents
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
  - [How to Create Firebase Service Account Key](#how-to-create-firebase-service-account-key)
- [Database Schema](#database-schema)
  - [Users](#users)
  - [Plants](#plants)
  - [Status](#status)
  - [Bookmarks](#bookmarks)
- [Documentation API](#documentation-api)
  - [Root](#root)
    - [GET /](#method-get)
  - [Auth](#auth)
    - [Register](#register)
    - [Login](#login)
    - [Update User](#updateuser)
    - [Detail User](#detail-user)
  - [Bookmark](#bookmark)
    - [Add Bookmark](#add-bookmark)
    - [Get All Bookmarks](#get-all-bookmarks)
    - [Get Bookmark By ID](#getbookmarkbyid)
    - [Delete Bookmark](#deletebookmark)
  - [Status](#status)
    - [Add Status](#add-status)
    - [Get All Statuses](#get-all-statuses)
    - [Get Status By ID](#get-status-by-id)
    - [Delete Status](#delete-status)
  - [Detail Plants](#detail-plants)
    - [Get Plant Details](#get-plant-details)



  
# Prerequisites
- node 21 or later
  ```bash
     node -v
- Npm 10.8.0 or later
  ```bash
     npm -v
# Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/RevLinnn/Cloud-Computing.git
   cd Cloud_computing
2. **Install dependencies:**
     ```bash
     npm install
     ```

# Configuration
The project uses environment variables for configuration. Ensure you have set up a .env file with the necessary variables. You can copy .env.example as a template.

- `JWT_SECRET`: A secret key used for JWT token generation.
- `FIREBASE_PROJECT_ID:` The ID of your Firebase project.
- `FIREBASE_PRIVATE_KEY_ID:` The ID of the private key associated with your Firebase service account.
- `FIREBASE_PRIVATE_KEY:` The private key itself associated with your Firebase service account. Note: Ensure the new lines are properly handled as per the actual key format.
- `FIREBASE_CLIENT_EMAIL:` The email address associated with your Firebase service account.
- `FIREBASE_CLIENT_ID:` The client ID associated with your Firebase service account.
- `FIREBASE_AUTH_URI:` The URI for Firebase authentication.
- `FIREBASE_TOKEN_URI:` The URI for Firebase token authentication.
- `FIREBASE_AUTH_PROVIDER_X509_CERT_URL:` The URI for Firebase authentication provider's X.509 certificate.
- `FIREBASE_CLIENT_X509_CERT_URL:` The URI for Firebase client's X.509 certificate.

## How to Create Firebase Service Account Key
Follow these steps to generate a Firebase service account key and configure your `.env` file.

1. **Go to the Firebase Console:**
2. **Select Your Project:**
3. **Open Project Settings:**
4. **Navigate to Service Accounts Tab:**
5. **Generate New Private Key:**
6. **Confirm and Download Key:**
7. **Copy Necessary Fields to .env File:**

# Database Schema
This project uses Firestore as its database. Firestore is a NoSQL document-based database that is part of Firebase.

### Users
This collection stores information about users.
- `uid` (string): Unique identifier for the user.
- `name` (string): Name of the user.
- `email` (string): Email address of the user.
- `password` (string): Password of the user.

### Plants
This collection stores information about plants.
- `id` (string): Unique identifier for the plant.
- `nama_tanaman` (string): Name of the plant.
- `deskripsi` (string): Brief description of the plant.
- `ciri_ciri` (map): Characteristics of the plant.
- `cara_pengobatan` (map): Treatment methods for the plant.
- `cara_pengobatan_alami` (map): Natural treatment methods for the plant.

### Status
This collection stores the disease status of plants inputted by users.
- `userId` (string): Unique identifier of the user who inputs the status.
- `nama_tanaman` (string): Name of the plant with the disease status.
- `jenis_penyakit` (string): Type of disease affecting the plant.
- `imageUrl` (string): URL of the image related to the plant's disease status.
- `timestamp` (timestamp): Time when the status was inputted.

### Bookmarks
This collection stores plants bookmarked by users.
- `userId` (string): Unique identifier of the user who bookmarks the plant.
- `nama_tanaman` (string): Name of the bookmarked plant.
- `jenis_penyakit` (string): Type of disease noted on the bookmarked plant.
- `imageUrl` (string): URL of the image related to the bookmark.
- `timestamp` (timestamp): Time when the bookmark was created.

  
# Documentation API
API Backend App Sobat Tani (Auth User, Bookmark, Status, Detail Plant)
# 📁 root 
### Method: GET
>```
>/
>```
### Response: 200
```json
Hello World
```

⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃

# 📁 docs 
### Method: GET
>```
>/api-docs
>```
### Response: 200

⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃

# 📁 Auth 

## Register

Description: Registers a new user with email, name, and password.
    
- Headers:
    
    - `Content-Type: application/json`
### Method: POST
>```
>/auth/register
>```
### Body (**raw**)

```json
{
    "name": " C241-PS007",
    "email": "sobattani1@gmail.com",
    "password": "securepassword01234"
}
```

### Response: 200
```json
{
    "message": "Success"
}
```

### Response: 400
```json
{
    "message": "Masukkan Email dengan benar"
}
```


⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃
## Login

**Description:** Logs in a user using email and password  
  

### Request
    
- Headers:
    
    - `Content-Type: application/json`
### Method: POST
>```
>/auth/login
>```
### Body (**raw**)

```json
{
    "email": "sobattani1@gmail.com",
    "password": "securepassword01234"
}
```

### Response: 200
```json
{
    "message": "Success",
    "data": {
        "uid": "user-id",
        "name": " C241-PS007",
        "email": "sobattani1@gmail.com"
    },
    "token": "jwt-token"
}
```

### Response: 400
```json
{
    "message": "Password salah"
}
```


⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃


## UpdateUser

Updates the profile information of an authenticated user  

### Method: PUT
>```
>/auth/user
>```
### Headers

|Content-Type|Value|
|---|---|
|Authorization|Bearer |


### Body (**raw**)

```json
{
   "name": " C241-PS007new",
    "password": "newsecurepassword01234"
}
```

### Response: 200
```json
{
    "message": "Success",
    "data": {
        "uid": "user-id",
        "email": "sobattani1@gmail.com",
        "name": " C241-PS007new"
    }
}
```

⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃

##Detail User

Retrieves detailed information of the authenticated user

### Method: GET
>```
>/auth/user
>```
### Headers

|Content-Type|Value|
|---|---|
|Authorization|Bearer |

### Response: 200
```json
{
    "message": "success",
    "data": {
        "uid": "user-id",
        "email": "sobattani1@gmail.com",
        "name": " C241-PS007new"
    }
}
```


⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃
# 📁 Bookmark 

## Add Bookmark
Uploads an image and creates a bookmark entry for a plant disease

### Method: POST
>```
>/bookmark
>```
### Headers

|Content-Type|Value|
|---|---|
|Authorization|Bearer |
|Content-Type|multipart/form-data|


### Body

|Param|value|Type|
|---|---|---|
|nama_tanaman|nama Tanaman 2|text|
|jenis_penyakit|jenis penyakit|text|
|image|/C:/Users/ACER/Pictures/Screenshots/Cuplikan layar 2024-06-08 170053.png|file|
|||text|


### Response: 201
```json
{
    "message": "Succcess",
    "data": {
        "id": "bookmark-id",
        "userId": "user-id",
        "nama_tanaman": "nama Tanaman",
        "jenis_penyakit": "jenis penyakit",
        "imageUrl": "https://storage.googleapis.com/sobat-tani-project-425607.appspot.com/bookmark-image/c836a365-3f16-4ccc-9335-2657ede14c73.jpg",
        "timestamp": {}
    }
}
```

### Response: 400
```json
{
    "message": "Gambar tidak ditemukan"
}
```


⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃
## Get All Bookmarks

Retrieves all bookmark entries for the authenticated user

### Method: GET
>```
>/bookmark
>```
### Headers

|Content-Type|Value|
|---|---|
|Authorization|Bearer |


### Response: 200
```json
{
    "message": "Succecss",
    "data": [
        {
            "id": "bookmark-id",
            "nama_tanaman": "nama Tanaman 2",
            "jenis_penyakit": "jenis penyakit",
            "imageUrl": "https://storage.googleapis.com/sobat-tani-project-425607.appspot.com/bookmark-image/90733aed-a089-4778-8e97-94b666d754bf.jpg",
            "userId": "user-id",
            "timestamp": "2024-06-13 16:19:29"
        },
        {
            "id": "bookmark-id",
            "nama_tanaman": "nama Tanaman",
            "jenis_penyakit": "jenis penyakit",
            "imageUrl": "https://storage.googleapis.com/sobat-tani-project-425607.appspot.com/bookmark-image/c836a365-3f16-4ccc-9335-2657ede14c73.jpg",
            "userId": "user-id",
            "timestamp": "2024-06-13 16:11:53"
        }
    ]
}
```


⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃

## GetBookmarkById
Retrieves a specific bookmark entry by its ID  
  
### Method: GET
>```
>/bookmark/:id
>```

### Headers

|Content-Type|Value|
|---|---|
|Authorization|Bearer |


### Response: 404
```json
{
    "message": "Bookmark tidak ditemukan!"
}
```

### Response: 200
```json
{
    "message": "Success",
    "data": {
        "id": "bookmark-id",
        "nama_tanaman": "nama Tanaman",
        "jenis_penyakit": "jenis penyakit",
        "imageUrl": "https://storage.googleapis.com/sobat-tani-project-425607.appspot.com/bookmark-image/c836a365-3f16-4ccc-9335-2657ede14c73.jpg",
        "userId": "user-id",
        "timestamp": "2024-06-13 09:11:53"
    }
}
```

⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃

## deleteBookmark

Deletes a specific bookmark entry by its ID

### Method: DELETE
>```
>/bookmark/delete/:id
>```
### Headers

|Content-Type|Value|
|---|---|
|Authorization|Bearer |

### Response: 404
```json
{
    "message": "Bookmark tidak ditemukan!"
}
```

### Response: 200
```json
{
    "message": "Bookmark berhasil dihapus"
}
```


⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃
# 📁 Folder: Status 

## Add Status

Uploads an image and creates a status entry for a plant disease

### Method: POST
>```
>/status
>```
### Headers

|Content-Type|Value|
|---|---|
|Authorization|Bearer |
|Content-Type|multipart/form-data|


### Body

|Param|value|Type|
|---|---|---|
|nama_tanaman|Singkong|text|
|jenis_penyakit|Mosaik|text|
|image|/C:/Users/ACER/Pictures/Screenshots/Cuplikan layar 2024-06-08 170053.png|file|


### Response: 201
```json
{
    "message": "Success",
    "data": {
        "id": "status-id",
        "userId": "user-id",
        "nama_tanaman": "Singkong",
        "jenis_penyakit": "Mosaik",
        "imageUrl": "https://storage.googleapis.com/sobat-tani-project-425607.appspot.com/status-image/63728d81-6d31-4fbe-ab48-039cdd5418d2.jpg",
        "timestamp": {}
    }
}
```

### Response: 400
```json
{
    "message": "Masukkan gambar Tanaman!"
}
```


⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃

## Get All Statuses

Retrieves all status entries

### Method: GET
>```
>/status
>```
### Headers

|Content-Type|Value|
|---|---|
|Authorization|Bearer |

### Response: 200
```json
{
    "message": "Succecss",
    "data": [
        {
            "id": "status-id",
            "userId": "user-id",
            "imageUrl": "https://storage.googleapis.com/sobat-tani-project-425607.appspot.com/status-image/0c5c5013-129a-45fb-a863-f65707693297.jpg",
            "nama_tanaman": "Rekomendasi obat Penyakit Garis-Garis Coklat (Brown Streak Disease)",
            "jenis_penyakit": "Berikan tanaman nutrisi yang cukup untuk meningkatkan sistem kekebalan tanaman terhadap penyakit. Namun, hindari pemupukan yang berlebihan karena dapat membuat tanaman lebih rentan terhadap serangan penyakit.",
            "timestamp": "2024-06-08 17:34:03"
        },
        {
            "id": "status-id",
            "userId": "user-id",
            "imageUrl": "https://storage.googleapis.com/sobat-tani-project-425607.appspot.com/status-image/63728d81-6d31-4fbe-ab48-039cdd5418d2.jpg",
            "nama_tanaman": "Singkong",
            "jenis_penyakit": "Mosaik",
            "timestamp": "2024-06-13 09:34:08"
        },
        {
            "id": "status-id",
            "userId": "user-id",
            "imageUrl": "https://storage.googleapis.com/sobat-tani-project-425607.appspot.com/status-image/c1b58a6c-a71f-42c8-90c1-43900eaaabed.jpg",
            "nama_tanaman": "Rekomendasi obat Penyakit Garis-Garis Coklat (Brown Streak Disease)",
            "jenis_penyakit": "Berikan tanaman nutrisi yang cukup untuk meningkatkan sistem kekebalan tanaman terhadap penyakit. Namun, hindari pemupukan yang berlebihan karena dapat membuat tanaman lebih rentan terhadap serangan penyakit.",
            "timestamp": "2024-06-08 17:53:42"
        }
    ]
}
```


⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃
## Get Status By ID

Retreieves a specific status entry by its ID

### Method: GET
>```
>/status/:id
>```
### Headers

|Content-Type|Value|
|---|---|
|Authorization|Bearer |

### Response: 200
```json
{
    "message": "Success",
    "data": {
        "id": "status-id",
        "userId": "user-id",
        "nama_tanaman": "Rekomendasi obat Penyakit Garis-Garis Coklat (Brown Streak Disease)",
        "jenis_penyakit": "Berikan tanaman nutrisi yang cukup untuk meningkatkan sistem kekebalan tanaman terhadap penyakit. Namun, hindari pemupukan yang berlebihan karena dapat membuat tanaman lebih rentan terhadap serangan penyakit.",
        "imageUrl": "https://storage.googleapis.com/sobat-tani-project-425607.appspot.com/status-image/0c5c5013-129a-45fb-a863-f65707693297.jpg",
        "timestamp": "2024-06-08 17:34:03"
    }
}
```

### Response: 404
```json
{
    "message": "status tidak ditemukan!"
}
```


⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃
## Delete Status

Deletes a specific status entry by its ID, including the associated image

### Method: DELETE
>```
>/status/:id
>```
### Headers

|Content-Type|Value|
|---|---|
|Authorization|Bearer |

### Response: 200
```json
{
    "message": "Success"
}
```

### Response: 404
```json
{
    "message": "Status not found"
}
```


⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃
# 📁 detail-plants 

## Get Plant Details

Retrieves details of a plant based on the provided name and disease type

### Method: GET
>```
>/plant?jenis_penyakit=Hawar Bakteri
>```
### Headers

|Content-Type|Value|
|---|---|
|Authorization|Bearer |


### Query Params

|Param|value|
|---|---|
|nama_tanaman|Singkong|
|jenis_penyakit|Hawar Bakteri|


### Response: 200
```json
{
    "message": "Success",
    "data": [
        {
            "id": "plants-id",
            "ciri_ciri": {
                "0": "Daun menguning dan menggulung.",
                "1": "Bercak coklat pada daun yang bisa menyebar ke batang.",
                "2": "Luka pada batang yang mengeluarkan lendir bakteri."
            },
            "nama_tanaman": "Singkong",
            "jenis_penyakit": "Hawar Bakteri",
            "cara_pengobatan": {
                "0": "Penggunaan Bakterisida: Aplikasi bakterisida berbasis tembaga seperti tembaga oksiklorida dapat mengurangi infeksi bakteri.",
                "1": "Sanitasi Lapangan: Menghapus dan membakar bagian tanaman yang terinfeksi untuk mencegah penyebaran.",
                "2": "Penggunaan Benih Bebas Penyakit: Menanam benih yang telah disertifikasi bebas dari bakteri."
            },
            "deskripsi": "Penyakit hawar bakteri pada singkong disebabkan oleh bakteri Xanthomonas axonopodis pv. manihotis. Penyakit ini menyebabkan kerusakan pada daun, batang, dan akar singkong, mengakibatkan daun menguning dan menggulung, serta munculnya bercak-bercak coklat pada daun.",
            "cara_pengobatan_alami": {
                "0": "Penyemprotan Ekstrak Bawang Putih: Bawang putih memiliki sifat antibakteri yang dapat membantu mengurangi populasi bakteri.",
                "1": "Penanaman Varietas Tahan: Memilih varietas singkong yang lebih tahan terhadap hawar bakteri."
            }
        }
    ]
}
```

### Response: 400
```json
{
    "message": "Missing query parameters"
}
```


⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃


# Running the Application
To run the Sobat Tani backend application, execute the following command:
 ```bash
     npm run start
```
The application will start running on `http://localhost:3000/`.

Make sure you have the required dependencies installed and the necessary configurations set before running the application.

That's it! You have successfully set up and documented the Sobat Tani backend application.
