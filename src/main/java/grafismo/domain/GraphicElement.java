package grafismo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A GraphicElement.
 */
@Entity
@Table(name = "graphic_element")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GraphicElement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code")
    private String code;

    @JsonIgnoreProperties(value = { "graphicElement" }, allowSetters = true)
    @OneToOne(mappedBy = "graphicElement")
    private ActionKey keys;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public GraphicElement id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public GraphicElement name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public GraphicElement code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ActionKey getKeys() {
        return this.keys;
    }

    public void setKeys(ActionKey actionKey) {
        if (this.keys != null) {
            this.keys.setGraphicElement(null);
        }
        if (actionKey != null) {
            actionKey.setGraphicElement(this);
        }
        this.keys = actionKey;
    }

    public GraphicElement keys(ActionKey actionKey) {
        this.setKeys(actionKey);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GraphicElement)) {
            return false;
        }
        return id != null && id.equals(((GraphicElement) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GraphicElement{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
