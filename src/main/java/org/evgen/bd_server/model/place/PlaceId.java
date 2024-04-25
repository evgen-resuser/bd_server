package org.evgen.bd_server.model.place;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class PlaceId implements Serializable {

    public PlaceId(){}

    public PlaceId(int typeId) {
        this.typeId = typeId;
        this.id = System.identityHashCode(this);
    }

    public PlaceId(int id, int typeId) {
        this.id = id;
        this.typeId = typeId;
    }

    private int id;

    private int typeId;
}
