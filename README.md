## azure-sastknprvdr


This is implementation of Azure SASTokenProvider as part of hadoop Azure for the ADLS Gen2 Filesystem.This is not openly available in the hadoop-azure / azure sdk dependencies, this has to be implemented by the client and pass the token
```scala
  spark.conf.set(s"fs.azure.account.auth.type.$blobAccountName.dfs.core.windows.net", "SAS")
  spark.conf.set(s"fs.azure.sas.token.provider.type.$blobAccountName.dfs.core.windows.net", "com.avinash.azure.sastknprvdr.FixedSASTokenProvider")
  spark.conf.set(s"fs.azure.sas.fixed.token.$blobAccountName.dfs.core.windows.net", formattedSasToken)
```