**ALPHA** : A project to learn Spring Boot and Nuxt 3.

**IMPORTANT** : it's in active development, and it's a personal project used to learn Spring Boot and Nuxt 3.
You can use it at your own risk but I can't provide any garanty whatsoever.

# Introduction
The main goal of this project is to have a server that can store photos encrypted in the client-side, so that the server has the minimum privacy responsability.

It's meant to be paired with my project named photone, which is a Nuxt 3 Frontend.

The photos will be encrypted *BEFORE* sending it to the server, so that your privacy will be preserved.

In the end, the photos will be organized by album and groups. 

You'll be able to have as many spaces (Family / Friends / Work / Someone ) as group you create.

You'll be able to add tags to organize your photos. 


And maybe eventually a local AI could locally and privately extract more metadata to greatly improve search accuracy.

## Configuration

Copy `application.properties.example` to `application.properties` and adjust the values to match your environment. In particular, JWT related expirations can be tuned with the following keys:

- `security.jwt.login-expiration-milliseconds`
- `security.jwt.validate-expiration-milliseconds`

# 📦 Changelog – Spricture

This changelog tracks the main milestones and version evolution of the **Spricture** project, from MVP to a secure and synchronized desktop photo application.

---

## ✅ 1.0 – First Production Release

> Basic full-stack MVP to upload, store and display photos.

- [x] Build a minimal front-end to upload a photo
- [x] Create a back-end that receives and stores a sample image
- [x] Display the uploaded photo immediately in the front-end
- [x] Replace the temporary logic with real photo storage
- [x] Expose stored photos via back-end endpoints
- [x] Fetch and display photos from the back-end to the front-end
- [x] Handle multiple photos upload and retrieval
- [x] Add JWT-based authentication
- [X] Add user registration
- [X] User registration by mail validation
- [X] User can only see/update his own photos
- [X] User can delete photos
- [X] User registration by admin validation
- [X] Add a basic permission system (roles only)
- [X] Implement photo encryption before storage
- [X] PIN usage to wrap the master key that encrypt photo
- [X] Translating into french/english
- [ ] More check of ownership (ex: deleting)

---

## 🛠 1.1 – Password Reset

> Add user account recovery capability.

- [ ] Implement password reset via email/token

---

## 🗂 1.2 – Simple Albums

> Organize photos into user albums.

- [ ] Create albums
- [ ] Delete albums
- [ ] Add and remove photos from albums

---

## 👥 1.3 – User Groups

> Enable collaboration between users.

- [ ] Create groups
- [ ] Invite users to a group
- [ ] Implement group key management

---

## 🔗 1.4 – Sharing Photos & Albums

> Enable sharing of individual items, which implicitly creates a "group" between users.  
> This could evolve into a "friend" feature under the hood.

- [ ] Allow sending a photo or album to another user
- [ ] Automatically create a link/group between users when sharing occurs

---

## ❌ 1.5 – Revoking Shared Access

> Control access post-sharing.

- [ ] Revoke access to a shared photo/album by URL or by account

---

## 🏷 1.6 – Tag System

> Add metadata to organize and filter photos.

- [ ] Implement a general tagging system for photos

---

## 🔁 1.7 – Background Sync Daemon

> Automate upload from local directories.

- [ ] Build a background process to monitor folders and upload new photos

---

## 🚀 2.0 – Secure Desktop App with Auto-Sync

> Final target: A cross-platform desktop app (Nuxt + Spring) for end-to-end encrypted photo management.

- [ ] Build a desktop app using Nuxt and Spring Boot
- [ ] Securely store and sync local photos
- [ ] Integrate with the background sync system


# Architectural presentation

## Application package
In the application Folder, you can find the core application domains.
In each domain you can find their own service, controller, entity, repository and DTOs

### Medium package

#### Medium entity 
The medium entity (Medium Class), (singular of media), represents the metadata of a media file, which is the core of the app.
To improve the privacy of the photos, I use a UUID as an ID (you have no temporal information stored in the stored filename).
You have the owner, the name and non-repudiation data (deletion/creation/modification date)

It will have extra metadata in the future.

The id will be the link with the file (uuid = filename).

#### MediumService
The medium service is a basic way to create a Medium in the DB (using the MediumRepository) and storing the binary in a file (using IUuidFileStorage).
It will enable the client to get a particular medium, or to delete/softDelete it.

Futur methods will take a user id and/or an album id, with the good permission, to provide only what the user can see.

#### Medium DTOs
Each DTO is used to represent the data sent to or received from the Front End.

There are adaptators used to "convert" between classes, they're implemented when needed.

### User
A basic way to store user info, right now there is no service to create a user.

A user will have different role on different photos/album (depending of the choice of the owner/creator)

### Album
An album has basic info that can help grouping photos. 

An Album has a many-to-many relationship with a Medium, this is represented by the AlbumMedium entity with some non-repudiation fields.
