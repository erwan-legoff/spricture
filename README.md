*ALPHA* : A project to learn Spring Boot and Nuxt 3.

*IMPORTANT* : it's in active development, and it's a personal project used to learn Spring Boot and Nuxt 3.
You can use it at your own risk but I can't provide any garanty whatsoever.

# Introduction
The main goal of this project is to have a server that can store photos encrypted in the client-side, so that the server has the minimum privacy responsability.

It's meant to be paired with my project named photone, which is a Nuxt 3 Frontend.

The photos will be encrypted *BEFORE* sending it to the server, so that your privacy will be preserved.

In the end, the photos will be organized by album and groups. 

You'll be able to have as many spaces (Family / Friends / Work / Someone ) as group you create.

You'll be able to add tags to organize your photos. 

And maybe eventually a local AI could locally and privately extract more metadata to greatly improve search accuracy.


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

### User
A basic way to store user info, right now there is no service to create a user.

A user will have different role on different photos/album (depending of the choice of the owner/creator)

### Album
An album has basic info that can help grouping photos. 

An Album has a many-to-many relationship with a Medium, this is represented by the AlbumMedium entity with some non-repudiation fields.
