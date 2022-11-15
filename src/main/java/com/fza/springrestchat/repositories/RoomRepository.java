package com.fza.springrestchat.repositories;

import com.fza.springrestchat.models.Room;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<Room, Integer> {
}
