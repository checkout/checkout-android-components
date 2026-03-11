package com.checkout.android.components.sample.ui.model

import com.checkout.components.interfaces.component.AddressConfiguration
import com.checkout.components.interfaces.model.AddressField
import com.checkout.components.interfaces.model.contact.Address
import com.checkout.components.interfaces.model.contact.ContactData
import com.checkout.components.interfaces.model.contact.Country
import com.checkout.components.interfaces.model.contact.Name
import com.checkout.components.interfaces.model.contact.Phone

sealed interface SampleAddressConfiguration {

  fun configuration(): AddressConfiguration?

  fun displayName(): String

  data object Empty : SampleAddressConfiguration {
    override fun configuration(): AddressConfiguration? = null

    override fun displayName(): String = "Empty"
  }

  data object PrefillShipping : SampleAddressConfiguration {
    override fun configuration(): AddressConfiguration = AddressConfiguration(
      data = fullAddress,
      fields = AddressField.shipping,
      onComplete = onComplete,
    )

    override fun displayName(): String = "Prefill Shipping"
  }

  data object PrefillCustomized : SampleAddressConfiguration {
    override fun configuration(): AddressConfiguration = AddressConfiguration(
      data = fullAddress,
      fields = listOf(
        AddressField.AddressLine1(isOptional = true),
        AddressField.AddressLine2(isOptional = false),
        AddressField.Phone(isOptional = false),
        AddressField.Email(isOptional = true),
        AddressField.State(isOptional = true),
        AddressField.Zip(isOptional = false),
      ),
      onComplete = onComplete,
    )

    override fun displayName(): String = "Prefill Customized"
  }

  data object NoPrefill : SampleAddressConfiguration {
    override fun configuration(): AddressConfiguration = AddressConfiguration(
      data = null,
      fields = AddressField.shipping,
      onComplete = onComplete,
    )
    override fun displayName(): String = "No Prefill"
  }

  data object AddressOnly : SampleAddressConfiguration {
    override fun configuration(): AddressConfiguration = AddressConfiguration(
      data = ContactData(
        address = fullAddress.address,
        phone = null,
        name = null,
        email = null,
      ),
      fields = AddressField.shipping,
      onComplete = onComplete,
    )

    override fun displayName(): String = "Address Only"
  }

  data object NoPhone : SampleAddressConfiguration {
    override fun configuration(): AddressConfiguration = AddressConfiguration(
      data = ContactData(
        address = fullAddress.address,
        phone = null,
        name = fullAddress.name,
        email = fullAddress.email,
      ),
      fields = AddressField.shipping,
      onComplete = onComplete,
    )

    override fun displayName(): String = "No Phone"
  }

  data object NoName : SampleAddressConfiguration {
    override fun configuration(): AddressConfiguration = AddressConfiguration(
      data = ContactData(
        address = fullAddress.address,
        phone = fullAddress.phone,
        name = null,
        email = fullAddress.email,
      ),
      fields = AddressField.shipping,
      onComplete = onComplete,
    )

    override fun displayName(): String = "No Name"
  }

  data object NoEmail : SampleAddressConfiguration {
    override fun configuration(): AddressConfiguration = AddressConfiguration(
      data = ContactData(
        address = fullAddress.address,
        phone = fullAddress.phone,
        name = fullAddress.name,
        email = null,
      ),
      fields = AddressField.shipping,
      onComplete = onComplete,
    )

    override fun displayName(): String = "No Email"
  }

  companion object Companion {

    private val onComplete: (ContactData?) -> Unit = {
      println("Collected address: $it")
    }

    private val fullAddress = ContactData(
      name = Name(
        firstName = "John",
        lastName = "Doe",
      ),
      email = "john.doe@checkout.com",
      phone = Phone(Country.UNITED_KINGDOM, "1234567890"),
      address = Address(
        country = Country.UNITED_KINGDOM,
        addressLine1 = "Wenlock Works",
        addressLine2 = "Shpeherdess Walk",
        city = "London",
        zip = "SW1A 2AA",
        state = "Greater London",
      ),
    )

    val entries: List<SampleAddressConfiguration> = listOf(
      Empty,
      PrefillShipping,
      PrefillCustomized,
      NoPrefill,
      AddressOnly,
      NoPhone,
      NoName,
      NoEmail,
    )
  }
}
