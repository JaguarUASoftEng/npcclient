package org.npc.test.api.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;
import org.npc.test.api.enums.ProductApiRequestStatus;
import org.npc.test.api.interfaces.ConvertToJsonInterface;
import org.npc.test.api.interfaces.LoadFromJsonInterface;
import org.npc.test.api.models.fieldnames.ProductFieldNames;

public class Product implements Parcelable, ConvertToJsonInterface, LoadFromJsonInterface<Product> {
    private UUID id;
    public UUID getId() {
        return this.id;
    }
    public Product setId(UUID id) {
        this.id = id;
        return this;
    }

    private String lookupCode;
    public String getLookupCode() {
        return this.lookupCode;
    }
    public Product setLookupCode(String lookupCode) {
        this.lookupCode = lookupCode;
        return this;
    }

    private String description;
    public String getDescription() {return this.description;}
    public Product setDescription(String description) {
        this.description = description;
        return this;
    }

    private boolean isActive;
    public boolean getIsActive() {
        return this.isActive;
    }
    public Product setIsActive(boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    private Double price;
    public Double getPrice() {
        return this.price;
    }
    public Product setPrice(Double price) {
        this.price = price;
        return this;
    }

    private int count;
    public int getCount() {
        return this.count;
    }
    public Product setCount(int count) {
        this.count = count;
        return this;
    }

    private Date createdOn;
    public Date getCreatedOn() {
        return this.createdOn;
    }
    public Product setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    private ProductApiRequestStatus apiRequestStatus;
    public ProductApiRequestStatus getApiRequestStatus() {
        return this.apiRequestStatus;
    }
    public Product setApiRequestStatus(ProductApiRequestStatus apiRequestStatus) {
        if (this.apiRequestStatus != apiRequestStatus) {
            this.apiRequestStatus = apiRequestStatus;
        }

        return this;
    }

    private String apiRequestMessage;
    public String getApiRequestMessage() {
        return this.apiRequestMessage;
    }
    public Product setApiRequestMessage(String apiRequestMessage) {
        if (!StringUtils.equalsIgnoreCase(this.apiRequestMessage, apiRequestMessage)) {
            this.apiRequestMessage = apiRequestMessage;
        }

        return this;
    }

    @Override
    public Product loadFromJson(JSONObject rawJsonObject) {
        String value = rawJsonObject.optString(ProductFieldNames.ID);
        if (!StringUtils.isBlank(value)) {
            this.id = UUID.fromString(value);
        }

        this.description = rawJsonObject.optString(ProductFieldNames.DESCRIPTION);
        this.lookupCode = rawJsonObject.optString(ProductFieldNames.LOOKUP_CODE);
        this.isActive = rawJsonObject.optBoolean(ProductFieldNames.IS_ACTIVE);
        this.price = rawJsonObject.optDouble(ProductFieldNames.PRICE);
        this.count = rawJsonObject.optInt(ProductFieldNames.COUNT, -1);

        value = rawJsonObject.optString(ProductFieldNames.CREATED_ON);
        if (!StringUtils.isBlank(value)) {
            try {
                this.createdOn = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(value);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        this.apiRequestMessage = rawJsonObject.optString(ProductFieldNames.API_REQUEST_MESSAGE);

        value = rawJsonObject.optString(ProductFieldNames.API_REQUEST_STATUS);
        if (!StringUtils.isBlank(value)) {
            this.apiRequestStatus = ProductApiRequestStatus.mapName(value);
        }

        return this;
    }

    @Override
    public JSONObject convertToJson() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put(ProductFieldNames.ID, this.id.toString());
            jsonObject.put(ProductFieldNames.LOOKUP_CODE, this.lookupCode);
            jsonObject.put(ProductFieldNames.COUNT, Integer.toString(this.count));
            jsonObject.put(ProductFieldNames.CREATED_ON, (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")).format(this.createdOn));
            jsonObject.put(ProductFieldNames.API_REQUEST_MESSAGE, this.apiRequestMessage);
            jsonObject.put(ProductFieldNames.API_REQUEST_STATUS, this.apiRequestStatus.name());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public int describeContents()
    {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags)
    {
        out.writeString(this.id.toString());
        out.writeString(this.lookupCode);
        out.writeString(this.description);
        out.writeString(Boolean.toString(isActive));
        out.writeDouble(this.price);
        out.writeInt(this.count);
        out.writeString((new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")).format(this.createdOn));
        out.writeString(this.apiRequestStatus.name());
        out.writeString(this.apiRequestMessage);
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>()
    {
        public Product createFromParcel(Parcel in)
        {
            return new Product(in);
        }

        public Product[] newArray(int size)
        {
            return new Product[size];
        }
    };

    private Product(Parcel in)
    {
        this.id = UUID.fromString(in.readString());
        this.lookupCode = in.readString();
        this.description = in.readString();
        this.isActive = in.readString().equals("true");
        this.price = in.readDouble();
        this.count = in.readInt();

        try {
            this.createdOn = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(in.readString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.apiRequestStatus = ProductApiRequestStatus.mapName(in.readString());
        this.apiRequestMessage = in.readString();
    }
    public Product() {
        this.count = -1;
        this.lookupCode = "";
        this.id = new UUID(0, 0);
        this.createdOn = new Date();
        this.apiRequestMessage = StringUtils.EMPTY;
        this.apiRequestStatus = ProductApiRequestStatus.OK;
    }
}
