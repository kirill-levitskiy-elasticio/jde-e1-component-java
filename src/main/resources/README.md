# mVISE Estimate
[GitHub Issue](https://github.com/elasticio/projects)

# Connectors to Build
## Opus Capita PIM 
### Task Description
Add easy demo catalog consisting of categories and products with descriptions and images/documents. Import these data to SaleSphere.

[GitHub Repository](https://github.com/OpusCapita)

### API Docs
For customers/By request

* Has a REST API
* Seems to have a complex structure
* List of objects is dynamic
* Object structure is dynamic

				<table>
					<tr>
						<td></td>
						<td>Description</td>
						<td>Estimation</td>
					</tr>
					<tr>
						<td>Component Development</td>
						<td>Authorization (1 Man Day setup)</td>
						<td>1 Man Day</td>
					</tr>
					<tr>
						<td></td>
						<td>Get New & Updated Objects Webhook (catalog, category, product, images/documents)</td>
						<td>1 Man Day setup + 1 MD per object</td>
					</tr>
					<tr>
						<td></td>
						<td>Bulk Extract Object (catalog, category, product)</td>
						<td>1 Man Day setup + 1 MD per object</td>
					</tr>
					<tr>
						<td></td>
						<td>Publish the app once finished</td>
						<td>1 Man Day</td>
					</tr>
					<tr>
						<td>Flow Development</td>
						<td>Get Objects from Opus Capita PIM and import these data to SaleSphere</td>
						<td>1.5 MD</td>
					</tr>
					<tr>
						<td>Human Problems</td>
						<td>Acceptance Testing, Mapping Verification, Documentation, Production Rollout, Architecture Review, Employee Training</td>
						<td>7.5 MD</td>
					</tr>
				</table>


|   | **Opus Capita PIM** | **Estimation** |
|:--|:--|:--|
| **Component Development** | <ul><li>Authorization (1 Man Day setup).</li><li>Get New & Updated Objects Webhook (1 Man Day setup + 1 MD per object: catalog, category, product, images/documents).</li><li>Bulk Extract Object (1 Man Day setup + 1 MD per object: catalog, category, product).</li><li>Publish the app once finished (1 Man Day)</li></ul> |
| **Flow Development** | 1.5 MD |
| **Human Problems** *(Acceptance Testing, Mapping Verification, Documentation, Production Rollout, Architecture Review, Employee Training)*  | 7.5 MD |

### Estimates
* Overhead to get started (2 Man Days)
* Authorization (1 Man Day setup)
* Get New & Updated Objects Webhook (1 Man Day setup + 1 MD per object: catalog, category, product, images/documents)
* Bulk Extract Object (1 Man Day setup + 1 MD per object: catalog, category, product)
* Publish the app once finished (1 Man Day)

## ContentServ PIM
### Task Description
Import the catalog structure with all categories and products. Import product descriptions and attached images, videos, documents.

### API Docs
For customers/By request

* Has a REST and XML API
* Seems to have a complex structure
* List of objects is dynamic
* Object structure is dynamic

### Estimates
* Overhead to get started (2 Man Days)
* Authorization (1 Man Day setup)
* Get New & Updated Objects Webhook (1 Man Day setup + 1 MD per object: catalog, category, product, images/documents)
* Bulk Extract Object (1 Man Day setup + 1 MD per object: catalog, category, product)
* Publish the app once finished (1 Man Day)

## eggheads PIM 
### Task Description
Import the catalog structure with all categories and products. Import product descriptions and attached images, videos, documents.

### API Docs
For customers/By request

* Has a XML API
* Seems to have a complex structure
* List of objects is dynamic
* Object structure is dynamic

### Estimates
* Overhead to get started (2 Man Days)
* Authorization (1 Man Day setup)
* Get New & Updated Objects Webhook (1 Man Day setup + 1 MD per object: catalog, category, product, images/documents)
* Bulk Extract Object (1 Man Day setup + 1 MD per object: catalog, category, product)
* Publish the app once finished (1 Man Day)

## pimcore PIM 
### Task Description
Add easy demo catalog consisting of categories and products with descriptions and images/documents. Import these data to SaleSphere.

### API Docs
[REST Webservice API](https://pimcore.com/docs/5.x/Development_Documentation/Web_Services/index.html)

* Has a REST API
* An API-key is unique for each user. Please be aware that the API Key changes when the user changes his/her password.
* The webservice API is not always the preferred way for importing/syncing data out of or into Pimcore. Often it's much more efficient to use the PHP API in custom scripts (CLI) or in a custom service endpoint.
* Returns JSON-encoded image/document data.
* Sends JSON-encoded image/document data.

### Estimates
* Overhead to get started (2 Man Days)
* Authorization (1 Man Day setup)
* Get New & Updated Objects Webhook (1 Man Day setup + 1 MD per object: catalog, category, product, images/documents)
* Bulk Extract Object (1 Man Day setup + 1 MD per object: catalog, category, product)
* Publish the app once finished (1 Man Day)

## Sharepoint Server 
### Task Description
Add several document of different type (Office Docs, PDFs, Videos) in several folders  Import them to SaleSphere Backend by using categories as folders, and Grid view template as carrier for every single document.

### API Docs
[API Docs](https://docs.microsoft.com/en-us/sharepoint/dev/general-development/choose-the-right-api-set-in-sharepoint)

* Has a REST/OData and JavaScript API
* OAuth authentication and authorization

### Estimates
* Overhead to get started (2 Man Days)
* Authorization (1 Man Day setup)
* Get New & Updated Objects Webhook (1 Man Day setup + 1 MD per object: catalog, category, product, images/documents)
* Bulk Extract Object (1 Man Day setup + 1 MD per object: catalog, category, product)
* Publish the app once finished (1 Man Day)

## SugarCRM 
### Task Description
Import contact data to fill most of our Salesphere backend CRM.

* Has a REST API
* It is already done

### Estimates
* Overhead to get started (1 Man Day)
* Check the app  (1 Man Day)

## SalesForce CRM
### Task Description
Import contact data to fill most of our Salesphere backend CRM.

* Has a REST API
* It is already done

### Estimates
* Overhead to get started (1 Man Day)
* Check the app  (1 Man Day)

## Sage CRM 
### Task Description
Import contact data to fill most of our Salesphere backend CRM.

* Has a REST API (js)

### Estimates
* Overhead to get started (2 Man Days)
* Authorization (1 Man Day setup)
* Get New & Updated Objects Webhook (1 Man Day setup + 1 MD for development import from contacts)
* Bulk Extract Object (1 Man Day setup + 1 MD for development import from contacts)
* Publish the app once finished (1 Man Day)

## CAS CRM
### Task Description
Import contact data to fill most of our Salesphere backend CRM.

### API Docs
For customers/By request

### Estimates
* Overhead to get started (2 Man Days)
* Authorization (1 Man Day setup)
* Get New & Updated Objects Webhook (1 Man Day setup + 1 MD for development import from contacts)
* Bulk Extract Object (1 Man Day setup + 1 MD for development import from contacts)
* Publish the app once finished (1 Man Day)

# Flows to Build
* Batching may eventually be required

# Open Questions:
* We need documentation: Opus Capita PIM, ContentServ PIM, eggheads PIM, CAS CRM
* For images/documents import for some integrations it will be base64-encoded string. Should we save it as is, or it should be converted to file?